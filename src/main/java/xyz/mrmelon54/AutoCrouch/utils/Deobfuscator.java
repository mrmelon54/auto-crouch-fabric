package xyz.mrmelon54.AutoCrouch.utils;

import com.google.common.net.UrlEscapers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.mapping.reader.v2.MappingGetter;
import net.fabricmc.mapping.reader.v2.TinyMetadata;
import net.fabricmc.mapping.reader.v2.TinyV2Factory;
import net.fabricmc.mapping.reader.v2.TinyVisitor;
import net.minecraft.MinecraftVersion;
import org.apache.commons.io.FileUtils;
import xyz.mrmelon54.AutoCrouch.client.AutoCrouchClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

// This deobfuscator is based on the StacktraceDeobfuscator from NotEnoughCrashes

public class Deobfuscator {
    private static final String MAPPINGS_JAR_LOCATION = "mappings/mappings.tiny";
    private static final String NAMESPACE_FROM = "intermediary";
    private static final String NAMESPACE_TO = "named";
    private static final Path CACHED_MAPPINGS = AutoCrouchClient.getInstance().getMappingsDir().resolve("mappings-" + MinecraftVersion.create().getName() + ".tiny");

    private static Map<String, String> mappings = null;

    // No need to deobf in dev
    private static final boolean DEBUG_DEOBF = false;
    private static final boolean ENABLE_DEOBF = (!FabricLoader.getInstance().isDevelopmentEnvironment() || DEBUG_DEOBF);

    public static void init() {
        if (!ENABLE_DEOBF) return;
        try {
            if (!Files.exists(CACHED_MAPPINGS)) downloadAndCacheMappings();
        } catch (Exception e) {
            AutoCrouchClient.getLogger().severe("Failed to load mappings! " + e);
        }
    }


    private static void downloadAndCacheMappings() {
        String yarnVersion;
        try {
            yarnVersion = YarnVersion.getLatestBuildForCurrentVersion();
        } catch (IOException e) {
            AutoCrouchClient.getLogger().info("Could not get latest yarn build for version: " + e);
            return;
        }

        AutoCrouchClient.getLogger().info("Downloading deobfuscation mappings: " + yarnVersion + " for the first launch");

        String encodedYarnVersion = UrlEscapers.urlFragmentEscaper().escape(yarnVersion);
        // Download V2 jar
        String artifactUrl = "https://maven.fabricmc.net/net/fabricmc/yarn/" + encodedYarnVersion + "/yarn-" + encodedYarnVersion + "-v2.jar";

        File jarFile = AutoCrouchClient.getInstance().getMappingsDir().resolve("yarn-mappings.jar").toFile();
        jarFile.deleteOnExit();
        try {
            FileUtils.copyURLToFile(new URL(artifactUrl), jarFile);
        } catch (IOException e) {
            AutoCrouchClient.getLogger().severe("Failed to downloads mappings! " + e);
            return;
        }

        try (FileSystem jar = FileSystems.newFileSystem(jarFile.toPath(), (ClassLoader) null)) {
            Files.copy(jar.getPath(MAPPINGS_JAR_LOCATION), CACHED_MAPPINGS, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            AutoCrouchClient.getLogger().severe("Failed to extract mappings! " + e);
        }
    }


    private static void loadMappings() {
        if (!Files.exists(CACHED_MAPPINGS)) {
            AutoCrouchClient.getLogger().warning("Could not download mappings, stack trace won't be deobfuscated");
            return;
        }

        Map<String, String> mappings = new HashMap<>();

        try (BufferedReader mappingReader = Files.newBufferedReader(CACHED_MAPPINGS)) {
            TinyV2Factory.visit(mappingReader, new TinyVisitor() {
                private final Map<String, Integer> namespaceStringToColumn = new HashMap<>();

                private void addMappings(MappingGetter name) {
                    mappings.put(name.get(namespaceStringToColumn.get(NAMESPACE_FROM)).replace('/', '.'),
                            name.get(namespaceStringToColumn.get(NAMESPACE_TO)).replace('/', '.'));
                }

                @Override
                public void start(TinyMetadata metadata) {
                    namespaceStringToColumn.put(NAMESPACE_FROM, metadata.index(NAMESPACE_FROM));
                    namespaceStringToColumn.put(NAMESPACE_TO, metadata.index(NAMESPACE_TO));
                }

                @Override
                public void pushClass(MappingGetter name) {
                    addMappings(name);
                }

                @Override
                public void pushMethod(MappingGetter name, String descriptor) {
                    addMappings(name);
                }

                @Override
                public void pushField(MappingGetter name, String descriptor) {
                    addMappings(name);
                }
            });

        } catch (IOException e) {
            AutoCrouchClient.getLogger().severe("Could not load mappings: " + e);
        }

        Deobfuscator.mappings = mappings;
    }

    public static String deobfuscateClass(String aClass) {
        if (!ENABLE_DEOBF) return aClass;
        if (mappings == null) loadMappings();
        if (mappings == null) return "Obfuscated<" + aClass + ">";
        return mappings.get(aClass);
    }
}

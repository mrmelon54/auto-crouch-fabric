package net.onpointcoding.autocrouch.client;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.api.SyntaxError;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.onpointcoding.autocrouch.Utils;
import net.onpointcoding.autocrouch.config.AutoCrouchConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class AutoCrouchClient implements ClientModInitializer {
    public final static String InGameScreenPackage = "net.minecraft.client.gui.screen.ingame";
    public static AutoCrouchClient instance;
    public Jankson jankson;
    public AutoCrouchConfig config;
    private File configFile;

    @Override
    public void onInitializeClient() {
        instance = this;
        jankson = new Jankson.Builder().build();
        File configDir = FabricLoader.getInstance().getConfigDir().toFile();
        configFile = new File(configDir, "autocrouch.json5");
        if (configFile.exists()) {
            try {
                config = jankson.fromJson(jankson.load(configFile), AutoCrouchConfig.class);
            } catch (IOException | SyntaxError e) {
                System.out.println("Failed to load config so using default config");
                config = generateDefaultConfig();
            }
        } else {
            config = generateDefaultConfig();
            saveConfig();
        }
    }

    public static AutoCrouchClient getInstance() {
        return instance;
    }

    public void saveConfig() {
        try {
            FileWriter f = new FileWriter(configFile);
            f.write(jankson.toJson(config).toJson(true, true));
            f.close();
        } catch (IOException e) {
            System.out.println("AutoCrouch failed to save config");
            e.printStackTrace();
        }
    }

    private AutoCrouchConfig generateDefaultConfig() {
        AutoCrouchConfig a = new AutoCrouchConfig();
        a.EnableGUIs = true;
        a.EnableChat = true;
        a.EnableTab = true;
        a.EnableScreensSet = Utils.getClassesExtendingBaseClass(InGameScreenPackage, Screen.class).stream().map(Class::getName).collect(Collectors.toSet());
        return a;
    }
}

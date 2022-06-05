package xyz.mrmelon54.AutoCrouch.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import xyz.mrmelon54.AutoCrouch.autoConfigScreen.ScreenConfigProvider;
import xyz.mrmelon54.AutoCrouch.config.ConfigStructure;
import xyz.mrmelon54.AutoCrouch.utils.Utils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class AutoCrouchClient implements ClientModInitializer {
    private static AutoCrouchClient instance;
    private ConfigStructure config;

    @Override
    public void onInitializeClient() {
        instance = this;

        GuiRegistry guiRegistry = AutoConfig.getGuiRegistry(ConfigStructure.class);
        guiRegistry.registerTypeProvider(new ScreenConfigProvider(), Map.class);

        AutoConfig.register(ConfigStructure.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ConfigStructure.class).getConfig();
        for (String s : getScreenClasses())
            if (!config.screenConfig.EnabledScreens.containsKey(s)) config.screenConfig.EnabledScreens.put(s, true);
    }

    public static AutoCrouchClient getInstance() {
        return instance;
    }

    public ConfigStructure getConfig() {
        return config;
    }

    private Set<String> getScreenClasses() {
        return Utils.getClassesExtendingBaseClass(Utils.InGameScreenPackage, Screen.class).stream().map(Class::getName).collect(Collectors.toSet());
    }
}

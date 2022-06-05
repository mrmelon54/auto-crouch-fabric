package xyz.mrmelon54.AutoCrouch.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@SuppressWarnings("unused")
@Config(name = "auto-crouch")
@Config.Gui.Background("minecraft:textures/block/dark_oak_log.png")
public class ConfigStructure implements ConfigData {
    @ConfigEntry.Category("main-config")
    @ConfigEntry.Gui.TransitiveObject
    public MainConfig mainConfig = new MainConfig();
    @ConfigEntry.Category("screen-config")
    @ConfigEntry.Gui.TransitiveObject
    public ScreenConfig screenConfig = new ScreenConfig();
}

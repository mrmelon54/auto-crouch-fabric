package xyz.mrmelon54.AutoCrouch.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "main-config")
public class MainConfig implements ConfigData {
    public boolean EnableGUIs = true;
    public boolean EnableTab = true;
    public boolean EnableChat = true;
}

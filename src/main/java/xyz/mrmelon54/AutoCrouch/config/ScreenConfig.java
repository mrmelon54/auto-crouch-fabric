package xyz.mrmelon54.AutoCrouch.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import xyz.mrmelon54.AutoCrouch.annotation.ScreenMapAnnotation;

import java.util.HashMap;
import java.util.Map;

@Config(name = "screen-config")
public class ScreenConfig implements ConfigData {
    @ScreenMapAnnotation
    public Map<String, Boolean> EnabledScreens = new HashMap<>();
}

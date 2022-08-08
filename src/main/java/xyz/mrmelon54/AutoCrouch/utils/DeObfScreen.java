package xyz.mrmelon54.AutoCrouch.utils;

import net.minecraft.client.gui.screen.Screen;

public class DeObfScreen {
    private final Class<? extends Screen> screen;
    private final String name;

    DeObfScreen(Class<? extends Screen> screen, String name) {
        this.screen = screen;
        this.name = name;
    }

    public Class<? extends Screen> getScreen() {
        return screen;
    }

    public String getName() {
        return name;
    }
}

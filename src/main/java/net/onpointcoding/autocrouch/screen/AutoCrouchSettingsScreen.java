package net.onpointcoding.autocrouch.screen;

import io.github.cottonmc.cotton.gui.GuiDescription;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

@Environment(EnvType.CLIENT)
public class AutoCrouchSettingsScreen extends BaseClosableScreen {
    public AutoCrouchSettingsScreen(GuiDescription description, Screen parent) {
        super(description, parent);
    }
}

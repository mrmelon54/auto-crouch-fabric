package net.onpointcoding.autocrouch;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.onpointcoding.autocrouch.gui.AutoCrouchSettingsGui;
import net.onpointcoding.autocrouch.screen.AutoCrouchSettingsScreen;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new AutoCrouchSettingsScreen(new AutoCrouchSettingsGui(), parent);
    }
}
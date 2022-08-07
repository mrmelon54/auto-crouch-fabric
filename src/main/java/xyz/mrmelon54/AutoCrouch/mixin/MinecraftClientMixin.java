package xyz.mrmelon54.AutoCrouch.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mrmelon54.AutoCrouch.client.AutoCrouchClient;
import xyz.mrmelon54.AutoCrouch.config.ConfigStructure;
import xyz.mrmelon54.AutoCrouch.duck.KeyBindingDuckProvider;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    @Nullable
    public ClientWorld world;

    @Shadow
    @Final
    public GameOptions options;

    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Inject(method = "setScreen", at = @At("TAIL"))
    private void injectedOpenScreen(Screen screen, CallbackInfo ci) {
        AutoCrouchClient autoCrouch = AutoCrouchClient.getInstance();
        if (screen != null) {
            if (this.world != null) {
                ConfigStructure config = autoCrouch.getConfig();
                if (this.player != null && this.player.isClimbing() && !this.player.getAbilities().flying) {
                    if (autoCrouch.getConfig().mainConfig.EnableChat && screen.getClass() == ChatScreen.class)
                        this.options.sneakKey.setPressed(true);
                    if (config.mainConfig.EnableGUIs && config.screenConfig.EnabledScreens.getOrDefault(screen.getClass().getName(), false))
                        this.options.sneakKey.setPressed(true);
                }
            }
        } else {
            if (this.options.sneakKey instanceof KeyBindingDuckProvider duckProvider)
                this.options.sneakKey.setPressed(duckProvider.isActuallyPressed());
        }
    }
}

package net.onpointcoding.autocrouch.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.world.ClientWorld;
import net.onpointcoding.autocrouch.client.AutoCrouchClient;
import net.onpointcoding.autocrouch.duck.KeyBindingDuckProvider;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "openScreen", at = @At("TAIL"))
    private void injectedOpenScreen(Screen screen, CallbackInfo ci) {
        AutoCrouchClient autoCrouch = AutoCrouchClient.getInstance();
        if (screen != null) {
            if (this.world != null) {
                if (autoCrouch.config.EnableChat && screen.getClass() == ChatScreen.class)
                    this.options.keySneak.setPressed(true);
                if (this.player != null && this.player.isClimbing() && autoCrouch.config.EnableGUIs && autoCrouch.config.EnableScreensSet.contains(screen.getClass().getName()))
                    this.options.keySneak.setPressed(true);
            }
        } else {
            if (this.options.keySneak instanceof KeyBindingDuckProvider duckProvider)
                this.options.keySneak.setPressed(duckProvider.isActuallyPressed());
        }
    }
}
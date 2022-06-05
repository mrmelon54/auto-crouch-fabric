package xyz.mrmelon54.AutoCrouch.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import xyz.mrmelon54.AutoCrouch.duck.KeyBindingDuckProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(KeyBinding.class)
public class KeyBindingMixin implements KeyBindingDuckProvider {
    @Shadow
    private InputUtil.Key boundKey;

    @Override
    public boolean isActuallyPressed() {
        long handle = MinecraftClient.getInstance().getWindow().getHandle();
        int code = boundKey.getCode();
        return InputUtil.isKeyPressed(handle, code);
    }
}

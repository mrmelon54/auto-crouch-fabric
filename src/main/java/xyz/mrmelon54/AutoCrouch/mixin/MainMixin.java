package xyz.mrmelon54.AutoCrouch.mixin;

import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.mrmelon54.AutoCrouch.utils.Utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(Main.class)
public class MainMixin {
    private static final Set<String> ignoredClasses = Arrays.stream(new String[]{
            "Position",
            "ScreenAccessor",
            "Narratable",
            "Drawable",
    }).collect(Collectors.toUnmodifiableSet());

    @Inject(method = "main", at = @At("HEAD"), cancellable = true)
    private static void injectedMain(String[] args, CallbackInfo ci) throws ClassNotFoundException {
        if (Objects.equals(System.getenv("AUTO_CROUCH_DEBUG"), "1")) {
            StringBuilder a = new StringBuilder();
            a.append("package xyz.mrmelon54.AutoCrouch.utils;\n\n");
            a.append("import net.minecraft.client.gui.screen.ingame.*;\n\n");
            a.append("import java.util.HashSet;\nimport java.util.Set;\n\n");
            a.append("public class Screens {\n");
            a.append("    static Set<DeObfScreen> screens = new HashSet<>();\n\n");
            a.append("    static {\n");
            Stream<String> s1 = Utils.getClassesForInGameScreens().stream().map(Class::getSimpleName);
            Stream<String> s2 = s1.filter(s -> !s.startsWith("Abstract") && !s.endsWith("Widget"));
            Stream<String> s3 = s2.filter(s -> !ignoredClasses.contains(s));
            s3.sorted().forEach(b -> {
                if (b.equals("")) return;
                a.append("        screens.add(new DeObfScreen(").append(b).append(".class, \"").append(b).append("\"));\n");
            });
            a.append("    }\n");
            a.append("}\n");
            System.out.println(a);
            ci.cancel();
        }
    }
}

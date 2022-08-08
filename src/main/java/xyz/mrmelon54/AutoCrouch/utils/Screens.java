package xyz.mrmelon54.AutoCrouch.utils;

import net.minecraft.client.gui.screen.ingame.*;

import java.util.HashSet;
import java.util.Set;

public class Screens {
    static Set<DeObfScreen> screens = new HashSet<>();

    static {
        screens.add(new DeObfScreen(AbstractCommandBlockScreen.class, "AbstractCommandBlockScreen"));
        screens.add(new DeObfScreen(HandledScreen.class, "HandledScreen"));
        screens.add(new DeObfScreen(MinecartCommandBlockScreen.class, "MinecartCommandBlockScreen"));
        screens.add(new DeObfScreen(ForgingScreen.class, "ForgingScreen"));
        screens.add(new DeObfScreen(AbstractFurnaceScreen.class, "AbstractFurnaceScreen"));
        screens.add(new DeObfScreen(SmithingScreen.class, "SmithingScreen"));
        screens.add(new DeObfScreen(SmokerScreen.class, "SmokerScreen"));
        screens.add(new DeObfScreen(FurnaceScreen.class, "FurnaceScreen"));
        screens.add(new DeObfScreen(BlastFurnaceScreen.class, "BlastFurnaceScreen"));
    }
}
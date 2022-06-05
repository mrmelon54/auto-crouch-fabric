package xyz.mrmelon54.AutoCrouch.utils;

import org.reflections.Reflections;

import java.util.Set;

public class Utils {
    public final static String InGameScreenPackage = "net.minecraft.client.gui.screen.ingame";

    public static <T> Set<Class<? extends T>> getClassesExtendingBaseClass(String prefix, Class<T> klass) {
        Reflections reflections = new Reflections(prefix);
        return reflections.getSubTypesOf(klass);
    }
}

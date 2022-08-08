package xyz.mrmelon54.AutoCrouch.utils;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    private final static String InGameScreenPackageName = "net.minecraft.client.gui.screen.ingame";

    public static Set<Class<?>> getClassesForInGameScreens() throws ClassNotFoundException {
        Reflections reflections = new Reflections(Utils.InGameScreenPackageName, Scanners.SubTypes.filterResultsBy(v -> true));
        Set<Class<?>> set = new HashSet<>();
        for (String s : reflections.getAll(Scanners.SubTypes)) {
            Class<?> aClass = Class.forName(s);
            set.add(aClass);
        }
        return Collections.unmodifiableSet(set);
    }

    public static Set<DeObfScreen> getInGameScreenClasses() {
        return Screens.screens;
    }
}

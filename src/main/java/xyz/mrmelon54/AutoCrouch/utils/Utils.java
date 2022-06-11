package xyz.mrmelon54.AutoCrouch.utils;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import org.reflections.Reflections;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    private final static String InGameScreenPackageName = "net.minecraft.client.gui.screen.ingame";
    private final static String InGameScreenObfPackage = BookScreen.class.getPackage().getName();

    public static Set<Class<? extends Screen>> getInGameScreenClasses() {
        Set<Class<? extends Screen>> classesExtendingBaseClass = getClassesExtendingBaseClass(InGameScreenObfPackage, Screen.class);
        Stream<Class<? extends Screen>> filteredStream = classesExtendingBaseClass.stream().filter((Predicate<Class<?>>) aClass -> {
            String s = Deobfuscator.deobfuscateClass(aClass.getName());
            return s.startsWith(InGameScreenPackageName + ".");
        });
        return filteredStream.collect(Collectors.toSet());
    }

    public static <T> Set<Class<? extends T>> getClassesExtendingBaseClass(String packageName, Class<T> klass) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(klass);
    }
}

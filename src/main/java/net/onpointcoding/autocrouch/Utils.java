package net.onpointcoding.autocrouch;

import org.reflections.Reflections;

import java.util.Set;

public class Utils {
    public static <T> Set<Class<? extends T>> getClassesExtendingBaseClass(String prefix, Class<T> klass) {
        Reflections reflections = new Reflections(prefix);
        return reflections.getSubTypesOf(klass);
    }
}

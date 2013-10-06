package me.taot.mcache.util;

import me.taot.mcache.CacheException;

import javax.persistence.Column;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CloneUtil {

    private static ConcurrentMap<Class, Cloner> cloners = new ConcurrentHashMap<>();

    private CloneUtil() {
    }

    public static <T> T clone(T obj) {
        if (obj == null) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        Cloner cloner = getCloner(clazz);
        return (T) cloner.doClone(obj);
    }

    private static Cloner getCloner(Class<?> clazz) {
        Cloner cloner = cloners.get(clazz);
        if (cloner == null) {
            Cloner newCloner = new Cloner(clazz);
            cloner = cloners.putIfAbsent(clazz, newCloner);
            if (cloner == null) {
                cloner = newCloner;
            }
        }
        return cloner;
    }


    /*
     * Cloner class
     */
    private static class Cloner {

        private final Constructor<?> constructor;
        private final Map<String, Accessor> accessorMap;

        Cloner(Class<?> clazz) {
            this.accessorMap = Collections.unmodifiableMap(createAccessorMap(clazz));
            try {
                this.constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new CacheException("Default constructor not found for class " + clazz.getName());
            }
        }

        Object doClone(Object obj) {
            try {
                Object newObj = constructor.newInstance();
                for (Accessor accessor : accessorMap.values()) {
                    Object value = accessor.getGetter().invoke(obj);
                    accessor.getSetter().invoke(newObj, value);
                }
                return newObj;
            } catch (Exception e) {
                throw new CacheException("Error cloning object: " + e.getMessage(), e);
            }
        }

        private Map<String, Accessor> createAccessorMap(Class<?> clazz) {
            Map<String, Accessor> accessorMap = new HashMap<>();
            while (clazz != null) {
                Method[] methods = clazz.getDeclaredMethods();
                if (methods != null || methods.length > 0) {
                    for (Method m : methods) {
                        String methodName = m.getName();
                        if (m.getAnnotation(Column.class) != null) {
                            Accessor accessor = getAccessor(clazz, m);
                            if (!accessorMap.containsKey(accessor.getName())) {
                                accessorMap.put(accessor.getName(), accessor);
                            }
                        }
                    }
                }
                clazz = clazz.getSuperclass();
            }
            return accessorMap;
        }

        private static Accessor getAccessor(Class<?> clazz, Method getter) {
            String capAttr = null;
            if (getter.getName().startsWith("get")) {
                capAttr = getter.getName().substring(3);
            } else if (getter.getName().startsWith("is")) {
                capAttr = getter.getName().substring(2);
            } else {
                throw new RuntimeException("Column annotation is not on getter for method " + getter.getName() + " on class " + clazz.getName());
            }

            Class<?> retType = getter.getReturnType();
            Method setter = null;
            try {
                setter = clazz.getDeclaredMethod("set" + capAttr, retType);
            } catch (NoSuchMethodException e) {
                // setter remains null
            }
            final String attr = decapitalize(capAttr);
            if (setter == null) {
                throw new RuntimeException("Cannot find setter for " + attr + " on class " + clazz.getName());
            }
            getter.setAccessible(true);
            setter.setAccessible(true);
            Accessor accessor = new Accessor(attr, getter, setter);
            return accessor;
        }

        private static String decapitalize(String name) {
            if (name == null || name.isEmpty()) {
                return name;
            }
            return Character.toLowerCase(name.charAt(0)) + name.substring(1);
        }
    }


    /*
     * Accessor class
     */
    private static class Accessor {
        private final String name;
        private final Method getter;
        private final Method setter;

        private Accessor(String name, Method getter, Method setter) {
            this.name = name;
            this.getter = getter;
            this.setter = setter;
        }

        private String getName() {
            return name;
        }

        private Method getGetter() {
            return getter;
        }

        private Method getSetter() {
            return setter;
        }
    }
}

package me.taot.mcache.util;

import me.taot.mcache.CacheException;

import javax.persistence.Column;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EntityUtil {

    private static ConcurrentMap<Class, Cloner> cloners = new ConcurrentHashMap<>();

    private EntityUtil() {
    }

    public static <T> T clone(T obj) {
        if (obj == null) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        Cloner cloner = getCloner(clazz);
        return (T) cloner.clone(obj);
    }

    public static <T> boolean equal(T o1, T o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 != null) {
            Class<?> clazz = o1.getClass();
            Cloner cloner = getCloner(clazz);
            return cloner.equal(o1, o2);
        }
        return false;
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

        boolean equal(Object o1, Object o2) {
            if (o1 == o2) {
                return true;
            }
            if (o1 == null || o2 == null) {
                return false;
            }
            if (!o1.getClass().equals(o2.getClass())) {
                return false;
            }
            boolean result = true;
            try {
                for (Accessor accessor : accessorMap.values()) {
                    Object v1 = accessor.getGetter().invoke(o1);
                    Object v2 = accessor.getGetter().invoke(o2);
                    if (v1 == v2) {
                        continue;
                    }
                    if (v1 == null || v2 == null) {
                        result = false;
                        break;
                    }
                    if (!v1.equals(v2)) {
                        result = false;
                        break;
                    }
                }
                return result;
            } catch (Exception e) {
                throw new CacheException("Error comparing objects: " + e.getMessage(), e);
            }
        }

        Object clone(Object obj) {
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

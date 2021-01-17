package me.icro.rpc.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lin
 * @version v 0.1 2021/1/17
 **/
public class SingletonFactory {
    private static final Map<String, Object> OBJECT_MAP = new HashMap<>();

    private SingletonFactory() {
    }

    public static <T> T getInstance(Class<T> c) {
        String key = c.toString();
        Object instance;
        synchronized (SingletonFactory.class) {
            instance = OBJECT_MAP.get(key);
            if (instance == null) {
                try {
                    instance = c.getDeclaredConstructor().newInstance();
                    OBJECT_MAP.put(key, instance);
                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        return c.cast(instance);
    }
}

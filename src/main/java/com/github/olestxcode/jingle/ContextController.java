package com.github.olestxcode.jingle;

public interface ContextController {

    ContextController add(Object object);

    ContextController add(Object object, String key);

    <T> T get(Class<T> clazz);

    Object get(String key);

    <T> T get(String key, Class<T> expectedType);

    int size();

    ContextController removePrimary(Class<?> clazz);

    ContextController removeSubordinates(Class<?> clazz);

    ContextController removeAll(Class<?> clazz);

    ContextController remove(String key);

    default ContextController remove(Object object) {
        return remove(object.getClass());
    }
}

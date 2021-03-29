package com.github.olestxcode.jingle.impl;

import com.github.olestxcode.jingle.ContextController;
import com.github.olestxcode.jingle.exception.ContextAlreadyRegisteredException;
import com.github.olestxcode.jingle.exception.ContextNotFoundException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JingleContextController implements ContextController {

    private static final ContextController APPLICATION_CONTEXT_CONTROLLER = new JingleContextController();

    public static ContextController defaultController() {
        return APPLICATION_CONTEXT_CONTROLLER;
    }

    private final AtomicInteger intKeyGenerator = new AtomicInteger();
    private final Map<Integer, Object> primaryContextMap = new HashMap<>();
    private final Map<String, Object> qualifierMap = new HashMap<>();
    private final Collection<ContextKey> registeredKeys = new HashSet<>();

    @Override
    public ContextController add(Object object) {
        ContextKey contextKey = getContextKey(object.getClass());
        if (primaryContextMap.containsKey(contextKey.getIntKey()))
            throw new ContextAlreadyRegisteredException(object);
        primaryContextMap.put(contextKey.getIntKey(), object);
        registeredKeys.add(contextKey);
        return this;
    }

    @Override
    public ContextController add(Object object, String key) {
        if (qualifierMap.containsKey(key))
            throw new ContextAlreadyRegisteredException(object);
        qualifierMap.put(key, object);
        return this;
    }

    @Override
    public <T> T get(Class<T> clazz) {
        ContextKey contextKey = getContextKey(clazz);
        if (primaryContextMap.containsKey(contextKey.getIntKey()))
            return (T) primaryContextMap.get(contextKey.getIntKey());
        else
            for (Object object : qualifierMap.values()) {
                if (object.getClass().isAssignableFrom(clazz))
                    return (T) object;
            }
        throw new ContextNotFoundException(clazz);
    }

    @Override
    public Object get(String key) {
        if (qualifierMap.containsKey(key))
            return qualifierMap.get(key);
        throw new ContextNotFoundException(key);
    }

    @Override
    public <T> T get(String key, Class<T> expectedType) {
        Object object = get(key);
        if (object.getClass().isAssignableFrom(expectedType))
            return (T) object;
        throw new ContextNotFoundException(expectedType);
    }

    @Override
    public int size() {
        return primaryContextMap.size() + qualifierMap.size();
    }

    @Override
    public ContextController removePrimary(Class<?> clazz) {
        ContextKey contextKey = getContextKey(clazz);
        primaryContextMap.remove(contextKey.getIntKey());
        registeredKeys.remove(contextKey);
        return this;
    }

    @Override
    public ContextController removeSubordinates(Class<?> clazz) {
        Collection<String> keysToRemove = new HashSet<>();
        for (Map.Entry<String, Object> entry : qualifierMap.entrySet())
            if (entry.getValue().getClass().isAssignableFrom(clazz))
                keysToRemove.add(entry.getKey());
        for (String key : keysToRemove)
            remove(key);
        return this;
    }

    @Override
    public ContextController removeAll(Class<?> clazz) {
        removePrimary(clazz);
        removeSubordinates(clazz);
        return this;
    }

    @Override
    public ContextController remove(String key) {
        qualifierMap.remove(key);
        return this;
    }

    private ContextKey getContextKey(Class<?> clazz) {
        ContextKey clazzContextKey = ContextKey.of(clazz);
        for (ContextKey contextKey : registeredKeys)
            if (contextKey.equals(clazzContextKey))
                return contextKey;
        return clazzContextKey.setIntKey(intKeyGenerator.getAndIncrement());
    }
}

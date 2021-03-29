package com.github.olestxcode.jingle.exception;

public class ContextNotFoundException extends RuntimeException {

    public ContextNotFoundException(Class<?> clazz) {
        super("Context not found for class " + clazz.getName());
    }

    public ContextNotFoundException(String key) {
        super("Context not found for key " + key);
    }
}

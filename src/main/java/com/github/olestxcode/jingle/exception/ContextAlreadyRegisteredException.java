package com.github.olestxcode.jingle.exception;

public class ContextAlreadyRegisteredException extends RuntimeException {

    public ContextAlreadyRegisteredException(Object object) {
        super("Context is already registered for class " + object.getClass().getName());
    }

    public ContextAlreadyRegisteredException(String key) {
        super("Context is already registered for key " + key);
    }
}

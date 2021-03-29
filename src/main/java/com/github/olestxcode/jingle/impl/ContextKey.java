package com.github.olestxcode.jingle.impl;

import java.util.Objects;

final class ContextKey {

    public static <T> ContextKey of(Class<T> contextBaseClass) {
        return new ContextKey(contextBaseClass);
    }

    private final Class<?> contextBaseClass;
    private int intKey;

    private ContextKey(Class<?> contextBaseClass) {
        this.contextBaseClass = Objects.requireNonNull(contextBaseClass);
    }

    public int getIntKey() {
        return intKey;
    }

    public ContextKey setIntKey(int intKey) {
        this.intKey = intKey;
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (!(object instanceof ContextKey))
            return false;

        ContextKey otherContextKey = (ContextKey) object;

        if (this.contextBaseClass == otherContextKey.contextBaseClass)
            return true;

        return this.contextBaseClass.isAssignableFrom(otherContextKey.contextBaseClass) || otherContextKey.contextBaseClass.isAssignableFrom(this.contextBaseClass);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contextBaseClass);
    }
}

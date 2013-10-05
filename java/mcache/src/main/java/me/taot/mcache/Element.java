package me.taot.mcache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Element<T> {

    private T value;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    Element() {
    }

    Element(T value) {
        this.value = value;
    }

    T getValue() {
        return value;
    }

    void setValue(T value) {
        this.value = value;
    }

    ReadWriteLock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "Element [" + value + "]";
    }
}

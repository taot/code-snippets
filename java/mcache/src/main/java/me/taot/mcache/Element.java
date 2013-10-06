package me.taot.mcache;

import me.taot.mcache.lock.ReadWriteLock;

public class Element<T> {

    private T value;

    private final ReadWriteLock lock = new ReadWriteLock();

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

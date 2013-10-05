package me.taot.mcache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractCache<T, K> implements Cache<T, K> {

    private ConcurrentMap<K, Element<T>> map = new ConcurrentHashMap<>();

    @Override
    public T get(K id) {
        Element<T> elem = map.get(id);
        if (elem == null) {
            return null;
        }
        Lock readLock = elem.getLock().readLock();
        readLock.lock();
        TransactionManager.current().addReadLock(readLock);
        T value = elem.getValue();
        if (value == null) {
            return null;
        } else {
            return clone(value);
        }
    }

    @Override
    public void put(K id, T value) {
        if (value == null) {
            return;
        }
        Element<T> elem = map.get(id);
        if (elem == null) {
            final Element<T> newE = new Element<T>();
            newE.getLock().writeLock().lock();
            elem = map.putIfAbsent(id, newE);
            if (elem == null) {
                elem = newE;
            } else {
                elem.getLock().writeLock().lock();
                newE.getLock().writeLock().unlock();
            }
        }
        TransactionManager.current().addWriteLock(elem.getLock().writeLock());
        T oldValue = elem.getValue();
        if (hasChanged(value, oldValue)) {
            elem.setValue(value);
            if (oldValue != null) {
                removeIndexes(oldValue);
            }
            if (value != null) {
                createIndexes(value);
            }
        }
    }

    @Override
    public void remove(K id) {
        Element<T> elem = map.remove(id);
        if (elem == null) {
            return;
        }
        TransactionManager.current().addWriteLock(elem.getLock().writeLock());
        T value = elem.getValue();
        if (value != null) {
            removeIndexes(value);
        }
    }

    abstract protected void createIndexes(T value);

    abstract protected void removeIndexes(T value);

    protected T clone(T value) {
        // TODO
        throw new UnsupportedOperationException();
    }

    protected boolean hasChanged(T value, T oldValue) {
        // TODO
        throw new UnsupportedOperationException();
    }

    protected Element<T> getElement(K id) {
        Element<T> e = map.get(id);
        if (e == null) {
            final Element<T> newE = new Element<T>();
            e = map.putIfAbsent(id, newE);
            if (e == null) {
                e = newE;
            }
        }
        return e;
    }
}

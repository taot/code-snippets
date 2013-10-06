package me.taot.mcache;

import me.taot.mcache.util.EntityUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;

public abstract class AbstractCache<T, K> implements Cache<T, K> {

    private ConcurrentMap<K, Element<T>> map = new ConcurrentHashMap<>();

    @Override
    public T get(K id) {
        Element<T> elem = map.get(id);
        if (elem == null) {
            return null;
        }
        elem.getLock().lockRead();
        TransactionManager.current().addLock(elem.getLock());
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
            newE.getLock().lockWrite();
            elem = map.putIfAbsent(id, newE);
            if (elem == null) {
                elem = newE;
            } else {
                elem.getLock().lockWrite();
                newE.getLock().unlock();
            }
        } else {
            elem.getLock().lockWrite();
        }
        TransactionManager.current().addLock(elem.getLock());
        T oldValue = elem.getValue();
        if (hasChanged(value, oldValue)) {
            removeIndexes(elem);
            T cloned = EntityUtil.clone(value);
            elem.setValue(cloned);
            createIndexes(elem);
        }
    }

    @Override
    public void remove(K id) {
        Element<T> elem = map.remove(id);
        if (elem == null) {
            return;
        }
        elem.getLock().lockWrite();
        TransactionManager.current().addLock(elem.getLock());
        removeIndexes(elem);
    }

    abstract protected void createIndexes(Element<T> e);

    abstract protected void removeIndexes(Element<T> e);

    protected T clone(T value) {
        return EntityUtil.clone(value);
    }

    protected boolean hasChanged(T value, T oldValue) {
        return !EntityUtil.equal(value, oldValue);
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

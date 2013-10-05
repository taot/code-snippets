package me.taot.mcache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractCache<T, K> implements Cache<T, K> {

    private ConcurrentMap<K, T> map = new ConcurrentHashMap<>();

    private ConcurrentMap<K, ReadWriteLock> locks = new ConcurrentHashMap<>();

    @Override
    public T get(K id) {
        ReadWriteLock lock = getReadWriteLock(id);
        lock.readLock().lock();
        TransactionManager.current().addReadLock(lock.readLock());
        T value = map.get(id);
        return clone(value);
    }

    @Override
    public void put(K id, T value) {
        ReadWriteLock lock = getReadWriteLock(id);
        lock.writeLock().lock();
        TransactionManager.current().addWriteLock(lock.writeLock());
        T cloned = clone(value);
        map.put(id, cloned);
        createIndexes(cloned);
    }

    @Override
    public void remove(K id) {
        ReadWriteLock lock = getReadWriteLock(id);
        lock.writeLock().lock();
        TransactionManager.current().addWriteLock(lock.writeLock());
        T value = map.remove(id);
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

    protected ReadWriteLock getReadWriteLock(K id) {
        ReadWriteLock lock = locks.get(id);
        if (lock == null) {
            final ReadWriteLock newLock = new ReentrantReadWriteLock();
            lock = locks.putIfAbsent(id, newLock);
            if (lock == null) {
                lock = newLock;
            }
        }
        return lock;
    }

}

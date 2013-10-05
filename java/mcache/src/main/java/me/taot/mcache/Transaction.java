package me.taot.mcache;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Transaction {

    private Lock myLock = new ReentrantLock();

    private Set<Lock> readLocks = new HashSet<>();

    private Set<Lock> writeLocks = new HashSet<>();

    void commit() {
        myLock.lock();
        try {
            for (Lock lock : readLocks) {
                lock.unlock();
            }
            readLocks.clear();
            for (Lock lock : writeLocks) {
                lock.unlock();
            }
            writeLocks.clear();
        } finally {
            myLock.unlock();
        }
    }

    void rollback() {
        throw new UnsupportedOperationException("rollback is not supported");
    }

    void addReadLock(Lock lock) {
        readLocks.add(lock);
    }

    void addWriteLock(Lock lock) {
        writeLocks.add(lock);
    }
}

package me.taot.mcache;

import me.taot.mcache.lock.ReadWriteLock;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class Transaction {

    private Set<ReadWriteLock> locks = new HashSet<>();

    void commit() {
        for (ReadWriteLock l : locks) {
            l.unlock();
        }
    }

    void rollback() {
        throw new UnsupportedOperationException("rollback is not supported");
    }

    void addLock(ReadWriteLock lock) {
        locks.add(lock);
    }
}

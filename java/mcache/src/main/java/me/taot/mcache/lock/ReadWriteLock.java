package me.taot.mcache.lock;

public class ReadWriteLock {

    private final java.util.concurrent.locks.ReentrantReadWriteLock underlying = new java.util.concurrent.locks.ReentrantReadWriteLock();

    public void lockRead() {
        underlying.readLock().lock();
    }

    public void lockWrite() {
        final int readHoldCount = underlying.getReadHoldCount();
        for (int i = 0; i < readHoldCount; i++) {
            underlying.readLock().unlock();
        }
        underlying.writeLock().lock();
    }

    public void unlock() {
        final int readHoldCount = underlying.getReadHoldCount();
        for (int i = 0; i < readHoldCount; i++) {
            underlying.readLock().unlock();
        }
        final int writeHoldCount = underlying.getWriteHoldCount();
        for (int i = 0; i < writeHoldCount; i++) {
            underlying.writeLock().unlock();
        }
    }
}

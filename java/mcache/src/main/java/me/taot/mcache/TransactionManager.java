package me.taot.mcache;

public class TransactionManager {

    private static ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<>();

    public static void start() {
        if (transactionThreadLocal.get() != null) {
            throw new CacheException("An mcache transaction already exists in current thread");
        }
        transactionThreadLocal.set(new Transaction());
    }

    public static Transaction current() {
        return transactionThreadLocal.get();
    }

    public static void commit() {
        Transaction current = transactionThreadLocal.get();
        if (current == null) {
            throw new CacheException("No mcache transaction exists in current thread");
        }
        try {
            current.commit();
        } finally {
            transactionThreadLocal.remove();
        }
    }

    public static void rollback() {
        Transaction current = transactionThreadLocal.get();
        if (current == null) {
            throw new CacheException("No mcache transaction exists in current thread");
        }
        try {
            current.rollback();
        } finally {
            transactionThreadLocal.remove();
        }
    }
}

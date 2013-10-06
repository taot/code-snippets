package me.taot.mcache;

public class Dsl {

    private Dsl() {
    }

    public static void transaction(Runnable runnable) {
        TransactionManager.start();
        try {
            runnable.run();
        } finally {
            TransactionManager.commit();
        }
    }
}

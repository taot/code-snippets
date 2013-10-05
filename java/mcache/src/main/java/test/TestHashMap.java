package test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestHashMap {

    public static void main(String[] args) throws Exception {
        new TestHashMap().run();
    }

    private void run() throws Exception {
        System.out.println("Started...");
        long start = System.currentTimeMillis();
        Map<Long, Position> cache = new ConcurrentHashMap<Long, Position>();
        Map<Long, Position> index = new ConcurrentHashMap<Long, Position>();
        Map<Long, ReadWriteLock> locks = new ConcurrentHashMap<Long, ReadWriteLock>();
        long num = 1000 * 1000 * 2;
        for (long i = 0; i < num; i++) {
            Position p = createPosition(i);
            cache.put(i, p);
            index.put(p.getAccountId(), p);
//            locks.put(i, new ReentrantReadWriteLock());
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Put duration: " + duration + "ms");

        start = System.currentTimeMillis();
        for (long i = 0; i < num; i++) {
//            Position p = cache.get(i);
            Position p = index.get(i);
        }
//        for (Map.Entry<Long, Position> ent: cache.entrySet()) {
//            Position p = ent.getValue();
//        }

        duration = System.currentTimeMillis() - start;
        System.out.println("Get duration: " + duration + "ms");

        System.out.println("Sleeping...");
        Thread.sleep(1000 * 60 * 10);

    }

    private Position createPosition(Long id) {
        Position p = new Position(id, id, id, "CNY" + id, id);
        return p;
    }
}

package test;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestInfinispan {

//    public static void main(String[] args) throws IOException {
//        Cache<String, Integer> c = new DefaultCacheManager("infinispan.xml").getCache("xml-configured-cache");
//        c.put("a", 1);
//        System.out.println(c.get("a"));
//        System.out.println(c);
//    }

    public static void main(String[] args) throws IOException {
        Cache<String, List<Long>> c = CacheProvider.get("mycluster", "tcp.xml");
        System.out.println(c);
        int count = 100000;
        while (true) {
            try {
                int v = count++;
                long start = System.currentTimeMillis();
                System.out.println("Updating " + v);
                List<Long> list = createList(v);
                c.put("a", list);
                long duration = System.currentTimeMillis() - start;
                System.out.println("Duration: " + duration + " ms");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private static List<Long> createList(int n) {
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < n; i++) {
            list.add(Long.valueOf(i));
        }
        return list;
    }
}

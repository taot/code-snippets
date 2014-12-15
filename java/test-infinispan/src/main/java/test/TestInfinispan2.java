package test;

import org.infinispan.Cache;

import java.io.IOException;
import java.util.List;

public class TestInfinispan2 {

//    public static void main(String[] args) throws IOException {
//        Cache<String, Integer> c = new DefaultCacheManager("infinispan.xml").getCache("xml-configured-cache");
//        c.put("a", 1);
//        System.out.println(c.get("a"));
//        System.out.println(c);
//    }

    public static void main(String[] args) throws IOException {
        Cache<String, List<Long>> c = CacheProvider.get("mycluster", "my-tcp2.xml");
//        c.put("a", 1);
        System.out.println(c);
        while (true) {
            try {
                long start = System.currentTimeMillis();
                List<Long> list = c.get(("a"));
                if (list != null) {
                    System.out.println(list.size());
                } else {
                    System.out.println("null");
                }
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
}

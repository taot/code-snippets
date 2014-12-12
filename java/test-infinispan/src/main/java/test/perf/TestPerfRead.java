package test.perf;

import org.infinispan.Cache;

import java.io.IOException;
import java.util.List;

public class TestPerfRead {

    public static void main(String[] args) throws IOException {
        Cache<String, List<Account>> c = CacheProvider.get();
        System.out.println(c);
        while (true) {
            long start = System.currentTimeMillis();
            List<Account> accounts = c.get("accounts");
            System.out.println(accounts.size());
            long duration = System.currentTimeMillis() - start;
            System.out.println("Duration: " + duration + " ms");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

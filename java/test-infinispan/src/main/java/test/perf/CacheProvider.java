package test.perf;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import java.io.IOException;
import java.util.List;

public class CacheProvider {

    public static Cache<String, List<Account>> get() throws IOException {
        DefaultCacheManager cm = new DefaultCacheManager("infinispan-replication.xml");
        Cache<String, List<Account>> c = cm.getCache("repl");
        return c;
    }
}

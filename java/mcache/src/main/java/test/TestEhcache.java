package test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.SearchAttribute;
import net.sf.ehcache.config.Searchable;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

public class TestEhcache {

    private CacheManager cacheManager = createCacheManager();

    private static CacheManager createCacheManager() {
        System.out.println("Configuring cache manager...");
        Configuration cacheManagerConfig = new Configuration();
        CacheConfiguration cacheConfig = new CacheConfiguration("myCache", 0).eternal(true);

        Searchable searchable = new Searchable();
        searchable.setAllowDynamicIndexing(true);
        cacheConfig.addSearchable(searchable);
        searchable.addSearchAttribute(new SearchAttribute().name("accountId"));
        searchable.addSearchAttribute(new SearchAttribute().name("currency"));

        CacheManager cacheManager = new CacheManager(cacheManagerConfig);
        cacheManager.addCache(new Cache(cacheConfig));
        return cacheManager;
    }

    public static void main(String[] args) throws Exception {
        new TestEhcache().run();
    }

    private void run() throws Exception {
        System.out.println("Start...");
        long start = System.currentTimeMillis();
        Cache cache = cacheManager.getCache("myCache");
//        Thread.sleep(100000);
        long num = 1000 * 1000 * 1;
        for (long i = 0; i < num; i++) {
            Position p = createPosition(i);
            Element elem = new Element(i, p);
            cache.put(elem);
            if (i % 1000 == 0) {
//                System.out.println("Putting " + i);
            }
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Put duration: " + duration + "ms");

        start = System.currentTimeMillis();
        for (long i = 0; i < num; i++) {
//            Element elem = cache.get(i);
//            if (elem == null) {
//                System.err.println("Cannot find element #" + i);
//            }
            Results results = cache.createQuery().includeValues()
                    .addCriteria(new Attribute("currency").eq("CNY" + i)).execute();
            if (results == null || results.size() == 0) {
                System.err.println("no results loaded for accountId " + i);
            } else {
                Result result = results.all().get(0);
                System.out.println(results.size() + " " + result.getValue());
            }

            if (i % 1000 == 0) {
//                System.out.println("Getting " + i);
            }
        }
        System.out.println("End.");
        duration = System.currentTimeMillis() - start;
        System.out.println("Get duration: " + duration + "ms");
    }

    private Position createPosition(Long id) {
        Position p = new Position(id, id, id, "CNY" + id, id);
        return p;
    }
}

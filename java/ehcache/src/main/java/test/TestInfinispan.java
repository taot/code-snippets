package test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.*;
import org.apache.lucene.util.Version;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.infinispan.manager.*;
import org.infinispan.*;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.SearchManager;

import java.util.List;

public class TestInfinispan {

    private DefaultCacheManager cacheManager = createCacheManager();

    private DefaultCacheManager createCacheManager() throws RuntimeException {
        try {
            return new DefaultCacheManager("sample-configurations.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws Exception {
        new TestInfinispan().run();
    }

    private void run() throws ParseException {
        /*
         * Populate cache
         */
        Cache<Object, Object> cache = cacheManager.getCache("LocalIndexed");
        long num = 1000 * 1000;
        long start = System.currentTimeMillis();
        for (long i = 0; i < num; i++) {
            Position p = createPosition(i);
            cache.put(i, p);
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("Put duration: " + duration + "ms");


        /*
         * Search cache
         */
        start = System.currentTimeMillis();
//        SearchManager searchManager = org.infinispan.query.Search.getSearchManager(cache);
//        org.apache.lucene.search.Query fullTextQuery = createLuceneQuery(searchManager, 1L);
//        CacheQuery cacheQuery = searchManager.getQuery( fullTextQuery );
//        List<Object> found = cacheQuery.list();
//        System.out.println("Found size: " + found.size());

        for (long i = 0; i < num; i++) {
            Position p = (Position) cache.get(i);
//            System.out.println(p);
        }
        duration = System.currentTimeMillis() - start;
        System.out.println("Get duration: " + duration + "ms");
    }

    private org.apache.lucene.search.Query createLuceneQuery(SearchManager searchManager, Long accountId) throws ParseException {
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
        QueryParser qp = new QueryParser(Version.LUCENE_CURRENT, "accountId", analyzer);
        org.apache.lucene.search.Query query = qp.parse(accountId.toString());
        return query;
    }

    private Position createPosition(Long id) {
        Position p = new Position(id, id, id, "CNY" + id, id);
        return p;
    }
}

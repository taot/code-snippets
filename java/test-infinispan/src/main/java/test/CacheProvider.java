package test;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class CacheProvider {

    public static Cache<String, List<Long>> get(String clusterName, String jgroupsConfigFile) throws IOException {

        Properties jgroupProps = new Properties();
        jgroupProps.setProperty("jgroups.tcpping.initial_hosts", "10.20.101.135[7800],10.20.101.135[7801]");

        GlobalConfiguration globalConfig = new GlobalConfigurationBuilder().transport()
                .defaultTransport()
                .clusterName(clusterName)
                .addProperty("configurationFile", jgroupsConfigFile)
                .withProperties(jgroupProps)
                //.machineId("qa-machine").rackId("qa-rack")
                .build();

        Configuration config = new ConfigurationBuilder()
                .clustering()
                .cacheMode(CacheMode.REPL_ASYNC)
                .eviction().maxEntries(2000).strategy(EvictionStrategy.LIRS)
                .build();
        String cacheName = "privilege";
        DefaultCacheManager cm = new DefaultCacheManager(globalConfig, config);

//        DefaultCacheManager cm = new DefaultCacheManager(resource);

        System.out.println("CacheManager: " + cm.getName());
        Cache<String, List<Long>> c = cm.getCache("privilege");
        return c;
    }
}

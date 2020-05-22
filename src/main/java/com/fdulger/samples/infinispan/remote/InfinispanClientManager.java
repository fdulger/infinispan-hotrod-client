package com.fdulger.samples.infinispan.remote;

import org.infinispan.client.hotrod.DefaultTemplate;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class InfinispanClientManager {

    String host = System.getProperty("infinispan.server.host", "127.0.0.1");
    int port = Integer.parseInt(System.getProperty("infinispan.server.port", ""+ConfigurationProperties.DEFAULT_HOTROD_PORT));

    RemoteCache registry;

    @Autowired
    RegistryEventService eventService;

    @PostConstruct
    private void init() {
        Configuration configuration = new ConfigurationBuilder().addServer().host(host).port(port).build();
        RemoteCacheManager cacheManager = new RemoteCacheManager(configuration);
        registry = cacheManager.administration()
                .withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
                .getOrCreateCache("registry", DefaultTemplate.DIST_SYNC);
        registry.addClientListener(eventService);
    }

    public Map<String, String> getCache() {
        return registry;
    }

}

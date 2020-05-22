package com.fdulger.samples.infinispan.remote;

import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryRemoved;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryModifiedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryRemovedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@ClientListener
public class RegistryEventService {

    @Autowired
    private SimpMessagingTemplate template;

    @ClientCacheEntryCreated
    public void entryCreated(ClientCacheEntryCreatedEvent<String> event) {
        template.convertAndSend("/topic/registry", "entryCreated: " + event.getKey());
    }

    @ClientCacheEntryModified
    public void entryModified(ClientCacheEntryModifiedEvent<String> event) {
        template.convertAndSend("/topic/registry", "entryModified: " + event.getKey());
    }

    @ClientCacheEntryRemoved
    public void entryRemoved(ClientCacheEntryRemovedEvent<String> event) {
        template.convertAndSend("/topic/registry", "entryRemoved: " + event.getKey());
    }

}
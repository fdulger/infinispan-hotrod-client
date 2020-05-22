package com.fdulger.samples.infinispan.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RegistryController {

    @Autowired
    InfinispanClientManager infinispanClient;

    @GetMapping("/registry")
    public Map<String,String> getAll() {
        return Collections.unmodifiableMap(getRegistry());
    }

    @GetMapping("/get")
    public String get(@RequestParam(value = "key") String key) {
        return getRegistry().get(key);
    }

    @PostMapping("/put")
    public void putAll(@RequestBody Map<String,String> all) {
        Map<String, String> registry = getRegistry();
        registry.keySet().removeAll(all.keySet().stream().filter(k -> all.get(k) == null || all.get(k).isEmpty()).collect(Collectors.toList()));
        registry.putAll(all.keySet().stream().filter(k -> all.get(k) != null && !all.get(k).isEmpty()).collect(Collectors.toMap(k -> k, k -> all.get(k))));
    }

    private Map<String,String> getRegistry() {
        return infinispanClient.getCache();
    }

}

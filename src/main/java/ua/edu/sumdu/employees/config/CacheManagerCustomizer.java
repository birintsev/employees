package ua.edu.sumdu.employees.config;

import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

import java.util.Collections;

/*@Component*/
public class CacheManagerCustomizer /*implements org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer<ConcurrentMapCacheManager>*/ {
    /*@Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(Collections.singletonList("images"));
    }*/
}

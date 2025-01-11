package org.example.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.example.model.ItemEntity;
import org.example.model.PedidoEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<Long, PedidoEntity> pedidoCache() {
        return Caffeine.newBuilder()
                .maximumSize(100) // Limita o tamanho do cache
                .expireAfterWrite(10, TimeUnit.MINUTES) // Expira após 10 minutos
                .build();
    }

    @Bean
    public Cache<Long, ItemEntity> itemCache() {
        return Caffeine.newBuilder()
                .maximumSize(100) // Limita o tamanho do cache
                .expireAfterWrite(10, TimeUnit.MINUTES) // Expira após 10 minutos
                .build();
    }
}

package org.example.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String clientId) {
        return buckets.computeIfAbsent(clientId, this::createNewBucket);
    }

    private Bucket createNewBucket(String clientId) {

        Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofSeconds(1)));
        return Bucket.builder().addLimit(limit).build();
    }
}

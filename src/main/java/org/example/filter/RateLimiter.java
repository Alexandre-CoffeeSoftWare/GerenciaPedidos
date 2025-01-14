package org.example.filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiter {

    private final Map<String, RequestInfo> requestCounts = new ConcurrentHashMap<>();
    private final int maxRequests;
    private final long timeWindow;

    public RateLimiter(int maxRequests, long timeWindow) {

        this.maxRequests = maxRequests;
        this.timeWindow = timeWindow;
    }

    public boolean checkRateLimit(String clientIP) {

        long currentTime = System.currentTimeMillis();
        RequestInfo requestInfo = requestCounts.computeIfAbsent(clientIP, ip -> new RequestInfo());

        synchronized (requestInfo) {

            if (currentTime - requestInfo.startTime > timeWindow) {
                requestInfo.startTime = currentTime;
                requestInfo.requestCount.set(0);
            }

            return requestInfo.requestCount.incrementAndGet() <= maxRequests;
        }
    }

    private static class RequestInfo {

        long startTime;
        AtomicInteger requestCount;

        public RequestInfo() {
            this.startTime = System.currentTimeMillis();
            this.requestCount = new AtomicInteger(0);
        }
    }
}
package org.example.ratelimit;

import io.github.bucket4j.*;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final ConcurrentHashMap<String, Bucket> buckets =
            new ConcurrentHashMap<>();

    public Bucket resolveBucket(String key) {

        return buckets.computeIfAbsent(
                key,
                this::newBucket
        );
    }

    private Bucket newBucket(String key) {

        Bandwidth limit = Bandwidth.builder()
                .capacity(100)
                .refillGreedy(
                        100,
                        Duration.ofMinutes(1))
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

}
package com.security.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.security.model.CachedValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LoginAttemptService {
    public static final int MAX_ATTEMPT = 3;
    public static final int BLOCK_DURATION_PER_SECOND = 300;

    private final LoadingCache<String, CachedValue> attemptsCache;

    public LoginAttemptService() {
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(BLOCK_DURATION_PER_SECOND, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public CachedValue load(String s) {
                        return new CachedValue(0, LocalDateTime.now());
                    }
                });
    }

    public void loginFailed(final String key) {
        CachedValue cachedValue = new CachedValue();
        try {
            cachedValue = attemptsCache.get(key);
            cachedValue.setAttempts(cachedValue.getAttempts() + 1);
        } catch (ExecutionException e) {
            cachedValue.setAttempts(0);
            log.error("Attempts to login exception");
        }

        if (isBlocked(key) && cachedValue.getBlockedTimestamp() == null) {
            cachedValue.setBlockedTimestamp(LocalDateTime.now());
        }
        attemptsCache.put(key, cachedValue);
    }

    public boolean isBlocked(String key) {

        try {
            return attemptsCache.get(key).getAttempts() >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            log.error("Attempts to login exception");
            return false;
        }
    }

    public CachedValue getCachedValue(String key) {
        return attemptsCache.getUnchecked(key);
    }
}

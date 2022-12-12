package com.space.starwars.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Fabio Barros
 * @version 1.0 created on 11/12/2022
 */
@ExtendWith(MockitoExtension.class)
class CacheServiceTest {

    private static final String REDIS_KEY = "key";
    private static final String REDIS_STRING_VALUE = "value";
    private static final LocalDate REDIS_DATE_VALUE = LocalDate.now();

    @InjectMocks
    private CacheService cacheService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private StringRedisTemplate redisTemplate;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Find key from Redis Cache")
    void testFindKeyFromRedisCache(){

        when(redisTemplate.opsForValue().get(REDIS_KEY)).thenReturn(REDIS_STRING_VALUE);

        var result = cacheService.findCache(REDIS_KEY, String.class);

        verify(redisTemplate, times(2)).opsForValue();
        assertEquals(REDIS_STRING_VALUE, result);
    }

    @Test
    @DisplayName("Find key from Redis Cache then throw exception")
    void testFindKeyFromRedisCacheThenThrowException(){

        when(redisTemplate.opsForValue()).thenThrow(NullPointerException.class);

        var result = cacheService.findCache(REDIS_KEY, String.class);

        verify(redisTemplate, times(1)).opsForValue();
        assertNull(result);
    }

    @Test
    @DisplayName("Find key from Redis Cache and value is an Object")
    void testFindKeyFromRedisCacheAndValueIsAnObject() throws JsonProcessingException {

        when(redisTemplate.opsForValue().get(REDIS_KEY)).thenReturn(String.valueOf(REDIS_DATE_VALUE));

        var result = cacheService.findCache(REDIS_KEY, LocalDate.class);

        verify(redisTemplate, times(2)).opsForValue();
        assertNull(result);
    }

    @Test
    @DisplayName("Find key from Redis Cache then throw exception while parsing object")
    void testFindKeyFromRedisCacheThenThrowExceptionWhileParsingObject() throws JsonProcessingException {

        when(redisTemplate.opsForValue().get(REDIS_KEY)).thenReturn(String.valueOf(REDIS_DATE_VALUE));
        when(objectMapper.readValue(String.valueOf(REDIS_DATE_VALUE), LocalDate.class)).thenThrow(NullPointerException.class);

        var result = cacheService.findCache(REDIS_KEY, LocalDate.class);

        verify(redisTemplate, times(2)).opsForValue();
        assertNull(result);
    }

    @Test
    @DisplayName("Upsert key in Redis Cache")
    void testUpsertKeyInRedisCache(){

        cacheService.updateCache(REDIS_KEY, REDIS_STRING_VALUE, Duration.ofHours(1));

        verify(redisTemplate, times(1)).opsForValue();
    }


}
package com.space.starwars.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Fabio Barros
 * @version 1.0 created on 11/12/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public void updateCache(String key, Object data, Duration duration){
        try{
            log.info("Saving data in cache with key="+ key);
            var value = data instanceof String ? (String) data : objectMapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(key, value, duration.toHours(), TimeUnit.HOURS);
        } catch (Exception e){
            log.error("Error while updating the cache");
        }
    }

    public <T> T findCache(String key, Class<T> clazz){
        var result = findCache(key);
        if(Objects.nonNull(result)){
            try {
                return String.class.equals(clazz)
                        ? clazz.cast(result)
                        : objectMapper.readValue(result, clazz);
            } catch (Exception e) {
                log.error("Exception while parsing the object from cache");
            }
        }

        return null;
    }

    private String findCache(String key){
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Error while getting the key from cache");
            return null;
        }
    }

}

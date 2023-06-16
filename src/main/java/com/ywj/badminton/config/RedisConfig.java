package com.ywj.badminton.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

    @Resource
    private RedisConnectionFactory factory;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(3600))  // 设置缓存有效期为1小时
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        log.warn("Redis occur exception, use custom CacheErrorHandler to handle");
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                doHandleRedisErrorException(exception, key);
            }
            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                doHandleRedisErrorException(exception, key);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                doHandleRedisErrorException(exception, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                doHandleRedisErrorException(exception, "");
            }
        };
    }

    protected void doHandleRedisErrorException(RuntimeException exception, Object key) {
        log.warn("Redis occur exception：key=[{}]", key, exception);
        String redisKey = (String) key;
        if (redisKey.contains("LOCK")) {
            throw exception;
        }
    }
}


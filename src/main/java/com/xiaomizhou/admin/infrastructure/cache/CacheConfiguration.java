package com.xiaomizhou.admin.infrastructure.cache;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/11/21
 */
//@Configuration
//@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration {

    /**
     * 使用redis做缓存中间件
     *
     * @param redisFactory
     * @param cacheProperties
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisFactory, CacheProperties cacheProperties) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(redisProperties.getTimeToLive());
        redisCacheConfiguration = redisCacheConfiguration.prefixCacheNameWith(redisProperties.getKeyPrefix());
        if (!redisProperties.isCacheNullValues()) redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
        if (!redisProperties.isUseKeyPrefix()) redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix();

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisFactory))
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}

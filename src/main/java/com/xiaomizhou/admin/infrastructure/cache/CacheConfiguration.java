package com.xiaomizhou.admin.infrastructure.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
//@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration {

    /**
     * 使用redis做缓存中间件
     *
     * @param redisFactory
     * @param cacheProperties
     * @return
     */
    //@Bean
    public CacheManager cacheManager(RedisConnectionFactory redisFactory, CacheProperties cacheProperties) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //将类名称序列化到json串中，去掉会导致得出来的的是LinkedHashMap对象，直接转换实体对象会失败
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));
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

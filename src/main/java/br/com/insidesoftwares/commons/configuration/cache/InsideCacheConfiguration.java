package br.com.insidesoftwares.commons.configuration.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class InsideCacheConfiguration {

    private final InsideCacheProperties insideCacheProperties;
    private final RedisProperties redisProperties;

    @Bean
    @Primary
    public LettuceConnectionFactory redisConnectionFactory() {

        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(redisProperties.getSentinel().getMaster());

        redisProperties.getSentinel().getNodes().forEach(s -> sentinelConfig.sentinel(s, redisProperties.getPort()));
        sentinelConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(sentinelConfig);
        lettuceConnectionFactory.setEagerInitialization(true);
        lettuceConnectionFactory.setValidateConnection(true);
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(insideCacheProperties.getRedisTimeToLive()))
                .disableCachingNullValues();
    }

    @Bean
    public RedisCacheManager cacheManager() {

        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();

        if(Objects.nonNull(insideCacheProperties.getCaches())) {
            insideCacheProperties.getCaches()
                    .forEach(
                            insideCacheModel -> cacheConfigs.put(insideCacheModel.getName(), buildRedisCacheConfig(insideCacheModel))
                    );

        }
        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(cacheConfiguration())
                .withInitialCacheConfigurations(cacheConfigs)
                .transactionAware()
                .build();
    }

    private RedisCacheConfiguration buildRedisCacheConfig(InsideCacheModel cachesProperties) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(cachesProperties.getTimeToLiveSeconds()))
                .disableCachingNullValues();
    }
}

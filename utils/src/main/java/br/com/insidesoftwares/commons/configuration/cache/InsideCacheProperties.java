package br.com.insidesoftwares.commons.configuration.cache;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConditionalOnProperty(
        prefix="spring.cache", name = "enable",
        havingValue = "true",
        matchIfMissing = true)
@Component
@ConfigurationProperties( prefix="insidesoftwares-cache")
@Data
public class InsideCacheProperties {

    private List<InsideCacheModel> caches;

    @Value("${insidesoftwares-cache.config.timeToLiveSeconds:3600}")
    private long redisTimeToLive;
}

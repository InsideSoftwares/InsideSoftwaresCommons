package br.com.insidesoftwares.commons.configuration.cache;

import lombok.Data;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
public class InsideCacheModel {
    private String name;
    private long timeToLiveSeconds;
}

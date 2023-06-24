package br.com.insidesoftwares.commons.configuration.cache;

import lombok.Data;

@Data
public class InsideCacheModel {
    private String name;
    private long timeToLiveSeconds;
}

package br.com.insidesoftwares.commons.lgpd.model;

import lombok.Builder;

@Builder
public record DataMask(
    String key,
    String newValue,
    boolean isRegex,
    String regex
) {}

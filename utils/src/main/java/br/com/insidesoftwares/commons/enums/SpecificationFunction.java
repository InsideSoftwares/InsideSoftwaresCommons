package br.com.insidesoftwares.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpecificationFunction {

    DAY("DAY"),
    MONTH("MONTH"),
    YEAR("YEAR");

    private final String function;

}

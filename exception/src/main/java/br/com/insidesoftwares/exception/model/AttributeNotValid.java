package br.com.insidesoftwares.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@Builder
public class AttributeNotValid implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String attribute;
    private final String message;

}

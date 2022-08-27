package br.com.insidesoftwares.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serial;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}

package br.com.insidesoftwares.commons.utils.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestDTO {

    private Long id;
    private String taxIdentifier;
    private String name;
    private String description;
    private boolean enable;
    private int age;

}

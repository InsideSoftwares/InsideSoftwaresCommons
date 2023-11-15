package br.com.insidesoftwares.commons.validation.zipcode.constraint;

import br.com.insidesoftwares.commons.validation.zipcode.ZipCode;

public record ZipCodeDTO(
        @ZipCode String zipCode
) {
}

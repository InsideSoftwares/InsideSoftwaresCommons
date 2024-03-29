package br.com.insidesoftwares.commons.dto.response;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public record InsidePaginatedDTO(
        int sizePerPage,
        int totalPages,
        long totalElements,
        long totalElementsPerPage
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1538642283241571187L;
}

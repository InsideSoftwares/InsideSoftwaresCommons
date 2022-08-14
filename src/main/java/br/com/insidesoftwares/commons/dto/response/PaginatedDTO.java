package br.com.insidesoftwares.commons.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class PaginatedDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int sizePerPage;
    private int totalPages;
    private long totalElements;

}

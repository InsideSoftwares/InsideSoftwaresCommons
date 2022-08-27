package br.com.insidesoftwares.commons.dto.response;

import br.com.insidesoftwares.commons.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatedDTO extends BaseDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    private int sizePerPage;
    private int totalPages;
    private long totalElements;

}

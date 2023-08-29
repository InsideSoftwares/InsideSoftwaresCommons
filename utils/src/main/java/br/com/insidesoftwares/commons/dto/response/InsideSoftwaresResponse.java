package br.com.insidesoftwares.commons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsideSoftwaresResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 4302753512896834219L;

    private String duration;
    private T data;
    private PaginatedDTO paginatedDTO;

}

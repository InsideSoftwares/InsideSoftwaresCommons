package br.com.insidesoftwares.commons.utils;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsideSoftwaresResponseUtils {

    public static <T> InsideSoftwaresResponse<T> wrapResponse(final T data){
        return InsideSoftwaresResponse.<T>builder()
                .data(data)
                .build();
    }

    public static <T> InsideSoftwaresResponse<T> wrapResponse(
            final T data,
            final int totalPages,
            final long totalElements,
            final int sizePerPage
    ){
        return InsideSoftwaresResponse.<T>builder()
                .data(data)
                .paginatedDTO(PaginationUtils.createPaginated(totalPages, totalElements, sizePerPage))
                .build();
    }

}

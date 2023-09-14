package br.com.insidesoftwares.commons.utils;

import br.com.insidesoftwares.commons.dto.response.InsideSoftwaresResponseDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsideSoftwaresResponseUtils {

    public static <T> InsideSoftwaresResponseDTO<T> wrapResponse(final T data){
        return InsideSoftwaresResponseDTO.<T>builder()
                .data(data)
                .build();
    }

    public static <T> InsideSoftwaresResponseDTO<T> wrapResponse(
            final T data,
            final int totalPages,
            final long totalElements,
            final long totalElementsPerPage,
            final int sizePerPage
    ){
        return InsideSoftwaresResponseDTO.<T>builder()
                .data(data)
                .insidePaginatedDTO(PaginationUtils.createPaginated(totalPages, totalElements, totalElementsPerPage, sizePerPage))
                .build();
    }

}

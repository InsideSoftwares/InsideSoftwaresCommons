package br.com.insidesoftwares.commons.utils;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.PaginatedDTO;
import br.com.insidesoftwares.commons.sort.PropertiesOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class PaginationUtils {

    public static PaginatedDTO createPaginated(final int totalPages, final long totalElements, final int sizePerPage){
        return PaginatedDTO.builder()
                .totalPages(totalPages)
                .totalElements(totalElements)
                .sizePerPage(sizePerPage)
                .build();
    }

    private static int calculatePage(final int page){
        return page - 1;
    }

    public static Pageable createPageable(
            final PaginationFilter paginationFilter
    ) {
        return PageRequest.of(
                calculatePage(paginationFilter.getPage()),
                paginationFilter.getSizePerPage(),
                createSort(paginationFilter.getDirection(), paginationFilter.getOrder())
        );
    }

    public static Sort createSort(
            final Sort.Direction direction,
            final String order
    ){
        return Sort.by(
                direction,
                order
        );
    }

}

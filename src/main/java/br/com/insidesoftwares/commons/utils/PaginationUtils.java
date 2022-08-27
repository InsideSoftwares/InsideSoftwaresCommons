package br.com.insidesoftwares.commons.utils;

import br.com.insidesoftwares.commons.dto.request.PaginationFilter;
import br.com.insidesoftwares.commons.dto.response.PaginatedDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

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
        Sort sort = createSort(paginationFilter.getDirection(), paginationFilter.getOrder());
        return Objects.nonNull(sort) ?
                PageRequest.of(
                    calculatePage(paginationFilter.getPage()),
                    paginationFilter.getSizePerPage(), sort
                ) :
                PageRequest.of(
                        calculatePage(paginationFilter.getPage()),
                        paginationFilter.getSizePerPage()
                );
    }

    private static Sort createSort(
            final Sort.Direction direction,
            final String order
    ){

        return Objects.nonNull(order) ?
                Sort.by(
                    direction,
                    order
                ) : null;
    }

}

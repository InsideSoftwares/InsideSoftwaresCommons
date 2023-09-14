package br.com.insidesoftwares.commons.utils;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsidePaginatedDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {

    public static InsidePaginatedDTO createPaginated(
            final int totalPages,
            final long totalElements,
            final long totalElementsPerPage,
            final int sizePerPage){
        return InsidePaginatedDTO.builder()
                .totalPages(totalPages)
                .totalElements(totalElements)
                .totalElementsPerPage(totalElementsPerPage)
                .sizePerPage(sizePerPage)
                .build();
    }

    private static int calculatePage(final int page){
        return page - 1;
    }

    public static Pageable createPageable(
            final InsidePaginationFilterDTO insidePaginationFilterDTO
    ) {
        Sort sort = createSort(insidePaginationFilterDTO.getDirection(), insidePaginationFilterDTO.getOrder());
        return Objects.nonNull(sort) ?
                PageRequest.of(
                    calculatePage(insidePaginationFilterDTO.getPage()),
                    insidePaginationFilterDTO.getSizePerPage(), sort
                ) :
                PageRequest.of(
                        calculatePage(insidePaginationFilterDTO.getPage()),
                        insidePaginationFilterDTO.getSizePerPage()
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

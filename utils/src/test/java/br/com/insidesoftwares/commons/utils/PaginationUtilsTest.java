package br.com.insidesoftwares.commons.utils;

import br.com.insidesoftwares.commons.dto.request.InsidePaginationFilterDTO;
import br.com.insidesoftwares.commons.dto.response.InsidePaginatedDTO;
import br.com.insidesoftwares.commons.sort.PropertiesOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaginationUtilsTest {

    private final static int TOTAL_PAGES = 10;
    private final static long TOTAL_ELEMENTS = 100;
    private final static int SIZE_PER_PAGE = 10;
    private final static int TOTAL_ELEMENTS_PER_PAGE = 10;

    @Test
    void createPaginated() {
        InsidePaginatedDTO insidePaginatedDTOExpected = InsidePaginatedDTO.builder()
                .totalPages(TOTAL_PAGES)
                .totalElements(TOTAL_ELEMENTS)
                .totalElementsPerPage(TOTAL_ELEMENTS_PER_PAGE)
                .sizePerPage(SIZE_PER_PAGE)
                .build();

        InsidePaginatedDTO insidePaginatedDTOResult = PaginationUtils.createPaginated(TOTAL_PAGES, TOTAL_ELEMENTS, TOTAL_ELEMENTS_PER_PAGE, SIZE_PER_PAGE);

        assertEquals(insidePaginatedDTOExpected.sizePerPage(), insidePaginatedDTOResult.sizePerPage());
        assertEquals(insidePaginatedDTOExpected.totalElements(), insidePaginatedDTOResult.totalElements());
        assertEquals(insidePaginatedDTOExpected.totalElementsPerPage(), insidePaginatedDTOResult.totalElementsPerPage());
        assertEquals(insidePaginatedDTOExpected.totalPages(), insidePaginatedDTOResult.totalPages());
    }

    @Test
    void createPageable() {
        InsidePaginationFilterDTO insidePaginationFilterDTO = InsidePaginationFilterDTO.builder()
                .page(1)
                .direction(Sort.Direction.DESC)
                .sizePerPage(25)
                .build();

        Pageable pageableExpected = PageRequest.of(
                0,
                insidePaginationFilterDTO.getSizePerPage()
        );

        Pageable pageableResult = PaginationUtils.createPageable(insidePaginationFilterDTO, OrderTest.ID);

        assertEquals(pageableExpected.getPageNumber(), pageableResult.getPageNumber());
        assertEquals(pageableExpected.getPageSize(), pageableResult.getPageSize());
        assertEquals(pageableExpected.getOffset(), pageableResult.getOffset());
        assertTrue(
                pageableExpected.getSort().stream().allMatch(
                        order -> pageableResult.getSort()
                                .get().allMatch(
                                        order1 -> order.getDirection().equals(order1.getDirection())
                                )
                )
        );
    }

    @Test
    void createPageableWithSort() {
        InsidePaginationFilterDTO insidePaginationFilterDTO = InsidePaginationFilterDTO.builder()
                .page(1)
                .direction(Sort.Direction.DESC)
                .sizePerPage(25)
                .order(OrderTest.ID.name())
                .build();

        Pageable pageableExpected = PageRequest.of(
                0,
                insidePaginationFilterDTO.getSizePerPage()
        );

        Pageable pageableResult = PaginationUtils.createPageable(insidePaginationFilterDTO, OrderTest.ID);

        assertEquals(pageableExpected.getPageNumber(), pageableResult.getPageNumber());
        assertEquals(pageableExpected.getPageSize(), pageableResult.getPageSize());
        assertEquals(pageableExpected.getOffset(), pageableResult.getOffset());
        assertTrue(
                pageableExpected.getSort().stream().allMatch(
                        order -> pageableResult.getSort()
                                .get().allMatch(
                                        order1 -> order.getDirection().equals(order1.getDirection())
                                )
                )
        );
    }

    @Getter
    @RequiredArgsConstructor
    static enum OrderTest implements PropertiesOrder {
        ID("id");
        private final String order;
        @Override
        public String properties() {
            return this.order;
        }

        @Override
        public String value(String name) {
            try{
                return OrderTest.valueOf(name).getOrder();
            }catch (Exception exception) {
                return this.getOrder();
            }
        }
    }

}

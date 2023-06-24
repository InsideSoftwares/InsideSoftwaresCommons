package br.com.insidesoftwares.commons.dto.request;

import br.com.insidesoftwares.commons.dto.BaseDTO;
import br.com.insidesoftwares.commons.sort.PropertiesOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

import java.io.Serial;
import java.util.Objects;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaginationFilter<T> extends BaseDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer page;
    private Integer sizePerPage;
    private Sort.Direction direction;
    private T order;

    public String getOrder() {
        return order instanceof PropertiesOrder ? ((PropertiesOrder) order).properties() : null;
    }

    public int getPage() {
        return Objects.isNull(page) ? 1 : page;
    }

    public int getSizePerPage() {
        return Objects.isNull(sizePerPage) ? 10 : sizePerPage;
    }

    public Sort.Direction getDirection() {
        return Objects.isNull(direction) ? Sort.Direction.ASC : direction;
    }
}

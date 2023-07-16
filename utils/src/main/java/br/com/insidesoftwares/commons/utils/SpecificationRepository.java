package br.com.insidesoftwares.commons.utils;

import br.com.insidesoftwares.commons.enums.SpecificationFunction;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class SpecificationRepository {

    public static <T> Specification<T> specificationBetween(final String field, final LocalDate startDate, final LocalDate endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(field), startDate, endDate);
    }

    public static <T> Specification<T> specificationGreaterThanOrEqualTo(final String field, final LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(field), date);
    }

    public static <T> Specification<T> specificationLessThanOrEqualTo(final String field, final LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(field), date);
    }

    public static <T, O> Specification<T> specificationEqual(final String field, final O value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value);
    }

    public static <T, C> Specification<T> specificationEqual(
            final String field,
            final SpecificationFunction function,
            final C value
    ) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.function(function.getFunction(), value.getClass(), root.get(field)), value);
    }

    public static <Y, T, O> Specification<T> specificationEqual(final O value, final String... fields) {
        return (root, query, criteriaBuilder) -> {
            Path<Y> path = getField(root, Arrays.stream(fields).toList());
            return criteriaBuilder.equal(path, value);
        };
    }

    private static <Y, T> Path<Y> getField(Root<T> root, List<String> fields) {
        Path<Y> path = root.get(fields.get(0));
        fields.remove(0);

        for(String field : fields) {
            path = getFieldPath(path, field);
        }

        return path;
    }

    private static <Y> Path<Y> getFieldPath(Path<Y> path, String field) {
        return path.get(field);
    }
}

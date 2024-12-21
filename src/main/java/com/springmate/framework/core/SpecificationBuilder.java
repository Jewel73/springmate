package com.springmate.framework.core;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jakarta.persistence.criteria.*;

public class SpecificationBuilder<T> {

    public Specification<T> buildSpecification(Message.FilterCriteria filterCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterCriteria != null && filterCriteria.getFilters() != null) {
                for (Map.Entry<String, Object> filter : filterCriteria.getFilters().entrySet()) {
                    addPredicate(predicates, root, criteriaBuilder, filter.getKey(), filter.getValue());
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void addPredicate(List<Predicate> predicates, Root<T> root,
                              CriteriaBuilder cb, String key, Object value) {
        if (value instanceof String) {
            predicates.add(cb.like(root.get(key), "%" + value + "%"));
        } else if (value instanceof Number) {
            predicates.add(cb.equal(root.get(key), value));
        } else if (value instanceof Boolean) {
            predicates.add(cb.equal(root.get(key), value));
        }
        // Add more type handlers as needed
    }

    public PageRequest buildPageRequest(Message.Pagination pagination,
                                        Message.FilterCriteria filterCriteria) {
        Sort sort = Sort.unsorted();
        if (filterCriteria != null && filterCriteria.getSortBy() != null) {
            sort = Sort.by(
                    filterCriteria.getSortOrder().equalsIgnoreCase("DESC") ?
                            Sort.Direction.DESC : Sort.Direction.ASC,
                    filterCriteria.getSortBy()
            );
        }

        return PageRequest.of(
                pagination.getPageNumber() != null ? pagination.getPageNumber() : 0,
                pagination.getPageSize() != null ? pagination.getPageSize() : 10,
                sort
        );
    }
}

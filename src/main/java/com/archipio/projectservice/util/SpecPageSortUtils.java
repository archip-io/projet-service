package com.archipio.projectservice.util;

import com.archipio.projectservice.persistence.entity.core.Project;
import lombok.experimental.UtilityClass;
import java.util.List;
import java.util.Map;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class SpecPageSortUtils {

    public static  Pageable generatePageable(
            int page, int pageSize, @NonNull Sort sort) {
        return PageRequest.of(page, pageSize).withSort(sort);
    }

    public static @NonNull Sort generateSort(
            List<String> sorts, @NonNull List<String> availableSortFields) {
        if (sorts == null) {
            sorts = List.of();
        }
        return sorts.stream()
                .filter(
                        s -> StringUtils.equalsAny(s, availableSortFields.toArray(new String[0])))
                .map(s -> s.contains("Asc") ? Sort.by(Sort.Direction.ASC, s.replace("Asc", ""))
                        : Sort.by(Sort.Direction.DESC, s.replace("Desc", "")))
                .reduce(Sort.unsorted(), Sort::and);
    }

    public static Specification<Project> generateProjectSpec(Map<String, String> filter) {
        if (filter == null) {
            return (root, query, builder) -> builder.conjunction();
        }
        return (root, query, builder) -> {
            var spec = builder.conjunction();

            if (filter.containsKey("ownerUsername")) {
                var concatParts =
                        List.of(
                                builder.concat(root.get("ownerUsername"), " "));
                var nameExpr = concatParts.stream().reduce(builder::concat).get();
                spec = builder.and(spec, builder.like(nameExpr, "%" + filter.get("ownerUsername") + "%"));
            }

            if (filter.containsKey("projectName")) {
                var concatParts =
                        List.of(
                                builder.concat(root.get("projectName"), " "));
                var nameExpr = concatParts.stream().reduce(builder::concat).get();
                spec = builder.and(spec, builder.like(nameExpr, "%" + filter.get("projectName") + "%"));
            }

            return spec;
        };
    }
}
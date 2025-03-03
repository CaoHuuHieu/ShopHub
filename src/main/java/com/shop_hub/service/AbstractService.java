package com.shop_hub.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.shop_hub.dto.pageable.organization.FilterDto;
import com.shop_hub.dto.pageable.organization.PageableRequestDto;
import com.shop_hub.dto.pageable.organization.PageableResponseDto;
import com.shop_hub.util.ObjectMapperUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractService<T, R> {

    public PageableResponseDto<R> getPage(PageableRequestDto input) {
        Specification<T> specification = createSpecification(input.getFilters());
        Pageable pageable = createPageable(input);
        Page<T> page = getPage(specification, pageable);
        return convertToResponse(page);
    }

    private PageableResponseDto<R> convertToResponse(Page<T> page) {
        List<R> data = page.stream().map(this::mapToDto).toList();
        return PageableResponseDto.<R>builder()
                .data(data)
                .size(page.getSize())
                .page(page.getNumber())
                .totalElement(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    public abstract R mapToDto(T object);

    public abstract Page<T> getPage(Specification<T> specification, Pageable pageable);

    private Pageable createPageable(PageableRequestDto input) {
        Sort sort = getSort(input.getSort());
        return PageRequest.of(input.getPage(), input.getSize(), sort);
    }

    private Sort getSort(String strSort) {
        List<Sort.Order> orders = new ArrayList<>();
        List<String> sorts = ObjectMapperUtils.readValue(strSort, new TypeReference<>() {
        });
        if(sorts == null)
            return Sort.unsorted();
        for(String sort : sorts) {
            String[] arr = sort.split(",");
            if(arr[1].equals("asc"))
                orders.add(new Sort.Order(Sort.Direction.ASC, arr[1]));
            else
                orders.add(new Sort.Order(Sort.Direction.DESC, arr[1]));
        }
        return Sort.by(orders);
    }

    public Specification<T> createSpecification(String strFilter) {
        Specification<T> specification = createDefaultSpecification();
        if(StringUtils.isBlank(strFilter))
            return specification;
        List<FilterDto> filters = ObjectMapperUtils.readValue(strFilter, new TypeReference<>() {});
        if(filters == null)
            return specification;

        for (FilterDto input : filters) {
            specification = specification.and(createSpecification(input));
        }
        return specification;

    }

    public abstract Specification<T> createDefaultSpecification();

    private Specification<T> createSpecification(FilterDto input) {
        return (root, query, criteriaBuilder) -> {
            if (input.getField().contains(".")) {
                String[] fieldParts = input.getField().split("\\.");
                String parentField = fieldParts[0];
                String childField = fieldParts[1];

                Join<T, ?> join = root.join(parentField, JoinType.LEFT);

                return switch (input.getOperator()) {
                    case "eq" -> criteriaBuilder.equal(join.get(childField),
                            castToRequiredType(join.get(childField).getJavaType(), input.getValue()));
                    case "ne" -> criteriaBuilder.notEqual(join.get(childField),
                            castToRequiredType(join.get(childField).getJavaType(), input.getValue()));
                    case "gt" -> criteriaBuilder.gt(join.get(childField),
                            (Number) castToRequiredType(join.get(childField).getJavaType(), input.getValue()));
                    case "lt" -> criteriaBuilder.lt(join.get(childField),
                            (Number) castToRequiredType(join.get(childField).getJavaType(), input.getValue()));
                    case "like" -> criteriaBuilder.like(join.get(childField),
                            "%" + input.getValue() + "%");
                    case "in" -> criteriaBuilder.in(join.get(childField))
                            .value(castToRequiredType(join.get(childField).getJavaType(), input.getValues()));
                    default -> throw new RuntimeException("Operation not supported yet");
                };
            }

            switch (input.getOperator()) {
                case "eq":
                    return criteriaBuilder.equal(root.get(input.getField()),
                            castToRequiredType(root.get(input.getField()).getJavaType(),
                                    input.getValue()));
                case "ne":
                    return criteriaBuilder.notEqual(root.get(input.getField()),
                            castToRequiredType(root.get(input.getField()).getJavaType(),
                                    input.getValue()));
                case "gt":
                    return criteriaBuilder.gt(root.get(input.getField()),
                            (Number) castToRequiredType(root.get(input.getField()).getJavaType(),
                                    input.getValue()));
                case "lt":
                    return criteriaBuilder.lt(root.get(input.getField()),
                            (Number) castToRequiredType(root.get(input.getField()).getJavaType(),
                                    input.getValue()));
                case "like":
                    return criteriaBuilder.like(root.get(input.getField()),
                            "%" + input.getValue() + "%");
                case "in":
                    return criteriaBuilder.in(root.get(input.getField()))
                            .value(castToRequiredType(root.get(input.getField()).getJavaType(),
                                    input.getValues()));
                default:
                    throw new RuntimeException("Operation not supported yet");
            }
        };
    }

    private Object castToRequiredType(Class fieldType, String value) {
        if(fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if(fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if(Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        }
        return null;
    }

    private Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }



}

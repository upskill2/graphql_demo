package com.course.product.specification;

import com.course.product.entity.ManufacturersEntity;
import org.springframework.data.jpa.domain.Specification;

public class ManufacturerSpecification extends BaseSpecification {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_ORIGIN_COUNTRY = "originCountry";

    public static Specification<ManufacturersEntity> nameContainsIgnoreCase (String keyword) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like (
                        criteriaBuilder.lower (root.get (FIELD_NAME)),
                        contains (keyword.toLowerCase ())
                ));
    }

    public static Specification<ManufacturersEntity> originCountryContainsIgnoreCase (String keyword) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like (
                        criteriaBuilder.lower (root.get (FIELD_ORIGIN_COUNTRY)),
                        contains (keyword.toLowerCase ())
                ));
    }

}

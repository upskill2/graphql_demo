package com.course.product.specification;

import com.course.product.entity.SeriesEntity;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import static com.course.product.specification.ManufacturerSpecification.FIELD_NAME;

public class SeriesSpecification extends BaseSpecification {

    public static final String FIELD_MANUFACTURER = "manufacturerId";

    public static Specification<SeriesEntity> seriesNameContainsIgnoreCase (String keyword) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like (
                        criteriaBuilder.lower (root.get (FIELD_NAME)),
                        contains (keyword.toLowerCase ())
                ));

    }

    public static Specification<SeriesEntity> manufacturerNameContainsIgnoreCase (String keyword) {


        return (root, query, criteriaBuilder) -> {
            final Path<Object> objectPath = root.join (FIELD_MANUFACTURER);
            return criteriaBuilder.like (
                    criteriaBuilder.lower (objectPath.get (FIELD_NAME)),
                    contains (keyword.toLowerCase ())
            );
        };

    }

    public static Specification<SeriesEntity> manufacturerOriginCountryContainsIgnoreCase (String keyword) {


        return (root, query, criteriaBuilder) -> {
            final Path<Object> objectPath = root.join (FIELD_MANUFACTURER);
            return criteriaBuilder.like (
                    criteriaBuilder.lower (objectPath.get (ManufacturerSpecification.FIELD_ORIGIN_COUNTRY)),
                    contains (keyword.toLowerCase ())
            );
        };

    }

}

package com.course.product.specification;

import com.course.product.domain.Enums.*;
import com.course.product.entity.ModelsEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static com.course.product.specification.ManufacturerSpecification.FIELD_NAME;
import static com.course.product.specification.ManufacturerSpecification.FIELD_ORIGIN_COUNTRY;
import static com.course.product.specification.SeriesSpecification.FIELD_MANUFACTURER;

public class ModelsSpecification extends BaseSpecification {

    public static final String FIELD_TRANSMISSION = "transmission";
    public static final String FIELD_IS_AVAILABLE = "isAvailable";
    public static final String SERIES_ENTITY = "seriesEntity";
    public static final String FIELD_COLOR = "exteriorColor";
    public static final String ON_THE_ROAD_PRICE = "onTheRoadPrice";


    public static Specification<ModelsEntity> priceIsGraterThan (int value) {
        return ((root, query, criteriaBuilder) -> (
                criteriaBuilder.greaterThanOrEqualTo (
                        root.get (ON_THE_ROAD_PRICE),
                        value
                )));
    }

    public static Specification<ModelsEntity> priceIsLessThan (int value) {
        return ((root, query, criteriaBuilder) -> (
                criteriaBuilder.lessThanOrEqualTo (
                        root.get (ON_THE_ROAD_PRICE),
                        value
                )));
    }

    public static Specification<ModelsEntity> priceIsBetween (int lower, int upper) {
        return ((root, query, criteriaBuilder) -> (
                criteriaBuilder.between (
                        root.get (ON_THE_ROAD_PRICE),
                        lower, upper
                )));
    }

    public static Specification<ModelsEntity> colorsListIgnoreCase (String colorList) {
        return ((root, query, criteriaBuilder) -> {
            List<String> list = List.of (colorList.split (","));
            List<Predicate> predicates = new ArrayList<> ();

            for (String color : list) {
                predicates.add (
                        criteriaBuilder.like (
                                criteriaBuilder.lower (root.get (FIELD_COLOR)),
                                contains (color.toLowerCase ())
                        ));
            }
            return criteriaBuilder.or (predicates.toArray (new Predicate[0]));
        });
    }


    public static Specification<ModelsEntity> modelNameContainsIgnoreCase (String keyword) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like (
                        criteriaBuilder.lower (root.get (FIELD_NAME)),
                        contains (keyword.toLowerCase ())
                ));

    }

    public static Specification<ModelsEntity> available (Boolean isAvailable) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal (
                        root.get (FIELD_IS_AVAILABLE),
                        isAvailable
                ));
    }

    public static Specification<ModelsEntity> transmissionContainsIgnoreCase (Transmission transmission) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal (
                        criteriaBuilder.lower (root.get (FIELD_TRANSMISSION)),
                        transmission.toString ())
        );

    }

    public static Specification<ModelsEntity> seriesNameContainsIgnoreCase (String keyword) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like (
                    criteriaBuilder.lower (root.join (SERIES_ENTITY).get (FIELD_NAME)),
                    contains (keyword.toLowerCase ())
            );
        };
    }

    public static Specification<ModelsEntity> manufacturerNameContainsIgnoreCase (String keyword) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like (
                    criteriaBuilder.lower (root.join (SERIES_ENTITY).join (FIELD_MANUFACTURER).get (FIELD_NAME)),
                    contains (keyword.toLowerCase ())
            );
        };
    }

    public static Specification<ModelsEntity> manufacturerOriginalCountryContainsIgnoreCase (String keyword) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like (
                    criteriaBuilder.lower (root.join (SERIES_ENTITY).join (FIELD_MANUFACTURER).get (FIELD_ORIGIN_COUNTRY)),
                    contains (keyword.toLowerCase ())
            );
        };
    }

}

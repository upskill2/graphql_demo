package com.course.specification;

import com.course.entity.CustomerEntity;
import com.course.sales.generated.types.Customer;
import com.course.specification.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class CustomerSpecification extends BaseSpecification {

    public static final String FIELD_UUID = "uuid";
    public static final String FIELD_EMAIL = "email";

    public static Specification<CustomerEntity> uuidEqualsIgnoreCase (String keyword) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal (
                    root.get (FIELD_UUID),
                    UUID.fromString (keyword.toLowerCase ()));
        };
    }

    public static Specification<CustomerEntity> emailContainsIgnoreCase (String keyword) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal (
                    criteriaBuilder.lower (root.get (FIELD_EMAIL)),
                    contains (keyword.toLowerCase ()));
        };
    }

}

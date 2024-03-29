package com.course.product.service;

import com.course.product.entity.ManufacturersEntity;
import com.course.product.generated.types.ManufacturerInput;
import com.course.product.generated.types.SortInput;
import com.course.product.repository.ManufacturersRepository;
import com.course.product.specification.ManufacturerSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService {


    @Autowired
    private ManufacturersRepository manufacturersRepository;


    public List<ManufacturersEntity> findManufacturers (Optional<ManufacturerInput> manufacturerInput) {
        ManufacturerInput input = manufacturerInput.orElse (new ManufacturerInput ());

        Specification<ManufacturersEntity> specification = Specification.where (
                StringUtils.isNoneBlank (input.getName ())
                        ? ManufacturerSpecification.nameContainsIgnoreCase (input.getName ()) : null
        ).and (StringUtils.isNoneBlank (input.getOriginCountry ())
                ? ManufacturerSpecification.originCountryContainsIgnoreCase (input.getOriginCountry ()) : null);

        SortInput sorts = manufacturerInput.get ().getSorts ();
        List<Sort.Order> order = sorts != null
                ? ManufacturerSpecification.sortOrdersFrom (List.of (sorts)) : Collections.EMPTY_LIST;

        return manufacturersRepository.findAll (specification, Sort.by (order));

    }
}

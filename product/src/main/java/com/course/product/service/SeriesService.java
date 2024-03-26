package com.course.product.service;

import com.course.product.entity.ManufacturersEntity;
import com.course.product.entity.SeriesEntity;
import com.course.product.generated.types.ManufacturerInput;
import com.course.product.generated.types.SeriesInput;
import com.course.product.repository.ManufacturersRepository;
import com.course.product.repository.SeriesRepository;
import com.course.product.specification.SeriesSpecification;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private ManufacturersRepository manufacturersRepository;

    public List<SeriesEntity> findSeriesByName (final String name) {
        return seriesRepository.findSeriesByNameIgnoreCase ("%" + name + "%");
    }

    public List<SeriesEntity> findSeriesByManufacturer (final String name) {
        UUID manufacturerId = manufacturersRepository.findByName (name)
                .stream ()
                .map (ManufacturersEntity::getUuid)
                .findFirst ().orElseThrow (() -> new DgsEntityNotFoundException ("No manufacturer found with the given name"));
        return seriesRepository.findSeriesByManufacturerUuid (manufacturerId);
    }

    public List<SeriesEntity> findSeries (Optional<SeriesInput> seriesInput) {

        SeriesInput input = seriesInput.orElse (new SeriesInput ());
        ManufacturerInput manufacturerInput = input.getManufacturer () != null ? input.getManufacturer () : new ManufacturerInput ();

        Specification<SeriesEntity> specification = Specification.where (
                StringUtils.isNoneBlank (input.getName ())
                        ? SeriesSpecification.seriesNameContainsIgnoreCase (input.getName ()) : null

        ).and (StringUtils.isNoneBlank (manufacturerInput.getName ())
                ? SeriesSpecification.manufacturerNameContainsIgnoreCase (manufacturerInput.getName ()) : null
        ).and (StringUtils.isNoneBlank (manufacturerInput.getOriginCountry ())
                ? SeriesSpecification.manufacturerOriginCountryContainsIgnoreCase (manufacturerInput.getOriginCountry ()) : null);

        return seriesRepository.findAll (specification);
    }
}

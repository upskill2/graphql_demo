package com.course.product.service;

import com.course.product.domain.Enums.*;
import com.course.product.entity.ManufacturersEntity;
import com.course.product.entity.ModelsEntity;
import com.course.product.entity.SeriesEntity;
import com.course.product.generated.types.ManufacturerInput;
import com.course.product.generated.types.ModelInput;
import com.course.product.generated.types.SeriesInput;
import com.course.product.repository.ManufacturersRepository;
import com.course.product.repository.ModelsRepository;
import com.course.product.repository.SeriesRepository;
import com.course.product.specification.ModelsSpecification;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModelService {

    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private ModelsRepository modelsRepository;
    @Autowired
    private ManufacturersRepository manufacturersRepository;

    public List<ModelsEntity> findModelByManufacturerName (final String name) {
        ManufacturersEntity manufacturer = manufacturersRepository.findByName (name).stream ().findFirst ().orElseThrow (
                () -> new DgsEntityNotFoundException ("Manufacturer not found"));
        List<UUID> seriesUUIDList = seriesRepository.findSeriesByManufacturerUuid (manufacturer.getUuid ())
                .stream ()
                .map (SeriesEntity::getUuid)
                .toList ();

        return modelsRepository.findModelsBySeriesUUID (seriesUUIDList);

    }

    public List<ModelsEntity> findModelByCountry (final String originCountry) {
        List<UUID> manufacturerUUIDList = manufacturersRepository.findAllByOriginCountryIgnoreCase (originCountry)
                .stream ()
                .map (ManufacturersEntity::getUuid)
                .toList ();
        List<UUID> seriesUUIDList = seriesRepository.findSeriesByManufacturerUuid (manufacturerUUIDList)
                .stream ()
                .map (SeriesEntity::getUuid)
                .toList ();

        return modelsRepository.findModelsBySeriesUUID (seriesUUIDList);
    }

    public List<ModelsEntity> findByModelName (final String name) {
        return modelsRepository.findByName (name);
    }

    public List<ModelsEntity> findModels (Optional<ModelInput> modelInput) {
        ModelInput input = modelInput.orElse (new ModelInput ());
        SeriesInput seriesInput = input.getSeries () != null ? input.getSeries () : new SeriesInput ();
        ManufacturerInput manufacturerInput = seriesInput.getManufacturer () != null ? seriesInput.getManufacturer () : new ManufacturerInput ();

        Specification<ModelsEntity> specification = Specification.where (
                        input.getIsAvailable () != null
                                ? ModelsSpecification.available (input.getIsAvailable ()) : null)
                .and (StringUtils.isNoneBlank (input.getName ())
                        ? ModelsSpecification.modelNameContainsIgnoreCase (input.getName ()) : null)
                .and (input.getTransmission () != null
                        ? ModelsSpecification.transmissionContainsIgnoreCase (Transmission.valueOf (input.getTransmission ().toString ())) : null)
                .and (seriesInput.getName () != null
                        ? ModelsSpecification.seriesNameContainsIgnoreCase (seriesInput.getName ()) : null
                ).and (StringUtils.isNoneBlank (manufacturerInput.getName ())
                        ? ModelsSpecification.manufacturerNameContainsIgnoreCase (manufacturerInput.getName ()) : null
                ).and (StringUtils.isNoneBlank (manufacturerInput.getOriginCountry ())
                        ? ModelsSpecification.manufacturerOriginalCountryContainsIgnoreCase (manufacturerInput.getOriginCountry ()) : null
                ).and (StringUtils.isNoneBlank (input.getExteriorColors ())
                        ? ModelsSpecification.colorsListIgnoreCase (input.getExteriorColors ()) : null);

        return modelsRepository.findAll (specification);

    }
}

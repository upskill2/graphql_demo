package com.course.product.service;

import com.course.product.domain.Enums.Transmission;
import com.course.product.entity.ManufacturersEntity;
import com.course.product.entity.ModelsEntity;
import com.course.product.entity.SeriesEntity;
import com.course.product.generated.types.*;
import com.course.product.repository.ManufacturersRepository;
import com.course.product.repository.ModelsRepository;
import com.course.product.repository.SeriesRepository;
import com.course.product.specification.ManufacturerSpecification;
import com.course.product.specification.ModelsSpecification;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    public List<ModelsEntity> findModels (Optional<ModelInput> modelInput, Optional<NumericComparisonInput> priceInput) {
        ModelInput input = modelInput.orElse (new ModelInput ());
        SeriesInput seriesInput = input.getSeries () != null ? input.getSeries () : new SeriesInput ();
        ManufacturerInput manufacturerInput = seriesInput.getManufacturer ()
                != null ? seriesInput.getManufacturer () : new ManufacturerInput ();

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
                        ? ModelsSpecification.colorsListIgnoreCase (input.getExteriorColors ()) : null)
                .and (getPriceSpecification (priceInput));

        List<Sort.Order> sort = input.getSorts () != null
                ? ManufacturerSpecification.sortOrdersFrom (List.of (input.getSorts ())) : Collections.emptyList ();

        return modelsRepository.findAll (specification, Sort.by (sort));

    }

    private Specification<ModelsEntity> getPriceSpecification (final Optional<NumericComparisonInput> priceInput) {
        NumericComparison operator = priceInput.orElse (new NumericComparisonInput ()).getOperator ();
        final int value = priceInput.orElseGet (NumericComparisonInput::new).getValue ();
        final int highValue = priceInput.orElse (new NumericComparisonInput ()).getHighValue () > value ?
                priceInput.orElse (new NumericComparisonInput ()).getHighValue () : value + 10000;
        return switch (operator) {
            case GREATER_THAN_EQUALS -> ModelsSpecification.priceIsGraterThan (value);
            case LESS_THAN_EQUALS -> ModelsSpecification.priceIsLessThan (value);
            default -> ModelsSpecification.priceIsBetween (value, highValue);
        };

    }
}

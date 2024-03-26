package com.course.product.component;

import com.course.product.generated.types.ManufacturerInput;
import com.course.product.generated.types.Model;
import com.course.product.generated.types.ModelInput;
import com.course.product.repository.ManufacturersRepository;
import com.course.product.service.ModelService;
import com.course.product.util.DomainMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@DgsComponent
public class ModelResolver {

    @Autowired
    private ModelService modelService;

    @Autowired
    private DomainMapper domainMapper;

    @DgsQuery
    public List<Model> modelsWithSpecification (@InputArgument Optional<ModelInput> modelInput) {
        return modelService.findModels (modelInput)
                .stream ()
                .map (domainMapper::toModels)
                .toList ();
    }

    @DgsQuery
    Set<Model> findModelByManufacturerName (ManufacturerInput manufacturerInput) {
        String name = manufacturerInput.getName ();
        return modelService.findModelByManufacturerName (name)
                .stream ()
                .map (domainMapper::toModels)
                .collect (Collectors.toSet ());
    }

    @DgsQuery
    List<Model> findModelByCountry (ManufacturerInput manufacturerInput) {
        return modelService.findModelByCountry (manufacturerInput.getOriginCountry ())
                .stream ()
                .map (domainMapper::toModels)
                .toList ();
    }

    @DgsQuery
    List<Model> findModelBySeriesName (ModelInput modelInput) {
        return findModelByName (modelInput);

    }

    @DgsQuery
    List<Model> findModelByName (ModelInput modelInput) {
        return modelService.findByModelName (modelInput.getName ())
                .stream ()
                .map (domainMapper::toModels)
                .toList ();
    }

    @DgsQuery
    List<Model> findModelByExteriorColor (ModelInput modelInput) {
        return null;
    }

    @DgsQuery
    List<Model> findModelByTransmission (ModelInput modelInput) {
        return null;
    }

    @DgsQuery
    List<Model> findModelByAvailability (ModelInput modelInput) {
        return null;

    }

    @DgsQuery
    List<Model> findModelByPrice (ModelInput modelInput) {
        return null;
    }
}

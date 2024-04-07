package com.course.product.controller;

import com.course.product.entity.ModelsEntity;
import com.course.product.generated.types.ModelSimple;
import com.course.product.service.ModelService;
import com.course.product.util.DomainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private DomainMapper domainMapper;

    @GetMapping (value = "/api/models/simple", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ModelSimple> findSimpleModels (@RequestParam (name = "modelUuids", required = true) final String modelUuuidsWithCommaSeparetor) {

        List<UUID> models = Arrays.stream (modelUuuidsWithCommaSeparetor
                        .split (","))
                .map (UUID::fromString)
                .toList ();
        return modelService.findAllModelsById (models)
                .stream ()
                .map (domainMapper::toModelSimple)
                .toList ();
    }

}

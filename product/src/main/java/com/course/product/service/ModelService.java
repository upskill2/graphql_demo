package com.course.product.service;

import com.course.product.entity.ManufacturersEntity;
import com.course.product.entity.ModelsEntity;
import com.course.product.entity.SeriesEntity;
import com.course.product.repository.ManufacturersRepository;
import com.course.product.repository.ModelsRepository;
import com.course.product.repository.SeriesRepository;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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
                .map (SeriesEntity::getId)
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
                .map (SeriesEntity::getId)
                .toList ();

        return modelsRepository.findModelsBySeriesUUID (seriesUUIDList);
    }

    public List<ModelsEntity> findByModelName (final String name) {
        return modelsRepository.findByName (name);
    }
}

package com.course.product.service;

import com.course.product.entity.ManufacturersEntity;
import com.course.product.entity.SeriesEntity;
import com.course.product.repository.ManufacturersRepository;
import com.course.product.repository.SeriesRepository;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}

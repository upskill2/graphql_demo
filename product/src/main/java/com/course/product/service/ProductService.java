package com.course.product.service;

import com.course.product.entity.ManufacturersEntity;
import com.course.product.repository.ManufacturersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ManufacturersRepository manufacturersRepository;

    public List<ManufacturersEntity> findManufacturerByName(String name) {
        return manufacturersRepository.findByName(name);
    }

    public List<ManufacturersEntity> findManufacturersByCountry (String country) {
        return manufacturersRepository.findAllByOriginCountryIgnoreCase (country);
    }
}

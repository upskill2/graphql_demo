package com.course.product.component;

import com.course.product.generated.types.Manufacturer;
import com.course.product.generated.types.ManufacturerInput;
import com.course.product.service.ProductService;
import com.course.product.util.DomainMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DgsComponent
public class ProductResolver {

    @Autowired
    private ProductService productService;
    @Autowired
    private DomainMapper domainMapper;

    @DgsQuery
    public List<Manufacturer> findManufacturerByName (@InputArgument ManufacturerInput manufacturerInput) {
        String name = manufacturerInput.getName ();
        return productService.findManufacturerByName ("%" + name + "%")
                .stream ()
                .map (domainMapper::toManufacturer)
                .toList ();
    }

    @DgsQuery
    public List<Manufacturer> findManufacturerByCountry (@InputArgument ManufacturerInput manufacturerInput) {
        return productService.findManufacturersByCountry (manufacturerInput.getOriginCountry ())
                .stream ()
                .map (domainMapper::toManufacturer)
                .toList ();
    }

}

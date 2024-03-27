package com.course.product.component;

import com.course.product.generated.types.Manufacturer;
import com.course.product.generated.types.ManufacturerInput;
import com.course.product.service.ManufacturerService;
import com.course.product.util.DomainMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class ManufacturerResolver {

    @Autowired
    private ManufacturerService manufacturerService;
    @Autowired
    private DomainMapper domainMapper;

    @DgsQuery
    public List<Manufacturer> manufacturersWithSpecification (@InputArgument Optional<ManufacturerInput> manufacturerInput) {
        return manufacturerService.findManufacturers (manufacturerInput)
                .stream ()
                .map (domainMapper::toManufacturer)
                .toList ();

    }

    @DgsQuery
    public Connection<Manufacturer> manufacturersPagination (
            @InputArgument Optional<ManufacturerInput> manufacturerInput,
            DataFetchingEnvironment dataFetchingEnvironment,
            @InputArgument Optional<Integer> first,
            @InputArgument Optional<Integer> last,
            @InputArgument Optional<String> after,
            @InputArgument Optional<String> before) {

        List<Manufacturer> allManufacturers = manufacturerService.findManufacturers (manufacturerInput)
                .stream ()
                .map (domainMapper::toManufacturer)
                .toList ();

        return new SimpleListConnection<> (allManufacturers).get (dataFetchingEnvironment);
    }

}

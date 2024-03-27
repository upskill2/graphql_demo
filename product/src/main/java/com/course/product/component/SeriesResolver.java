package com.course.product.component;

import com.course.product.generated.types.Series;
import com.course.product.generated.types.SeriesInput;
import com.course.product.service.SeriesService;
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
public class SeriesResolver {

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private DomainMapper domainMapper;

    @DgsQuery
    public Connection<Series> seriesPagination (
            @InputArgument SeriesInput seriesInput,
            DataFetchingEnvironment dataFetchingEnvironment,
            @InputArgument Optional<Integer> first,
            @InputArgument Optional<Integer> last,
            @InputArgument Optional<String> after,
            @InputArgument Optional<String> before) {

        List<Series> allSeries = seriesService.findSeries (Optional.of (seriesInput))
                .stream ()
                .map (domainMapper::toSeries)
                .toList ();

        return new SimpleListConnection<> (allSeries).get (dataFetchingEnvironment);

    }

    @DgsQuery
    public List<Series> seriesWithSpecification (Optional<SeriesInput> seriesInput) {
        return seriesService.findSeries (seriesInput)
                .stream ()
                .map (domainMapper::toSeries)
                .toList ();
    }

    @DgsQuery
    public List<Series> findSeriesByName (@InputArgument SeriesInput seriesInput) {
        String name = seriesInput.getName ();
        return seriesService.findSeriesByName ("%" + name + "%")
                .stream ()
                .map (domainMapper::toSeries)
                .toList ();
    }

    @DgsQuery
    public List<Series> findSeriesByManufacturer (@InputArgument SeriesInput seriesInput) {
        return seriesService.findSeriesByManufacturer (seriesInput.getManufacturer ().getName ())
                .stream ()
                .map (domainMapper::toSeries)
                .toList ();
    }

}

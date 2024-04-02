package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeStockDatasource;
import com.course.graphql.generated.types.Stock;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.util.List;

@DgsComponent
public class FakeStockDataResolver {

    @Autowired
    private FakeStockDatasource fakeStockDatasource;


    @DgsSubscription
    public Publisher<List<Stock>> randomStock () {
        return Flux.interval (java.time.Duration.ofSeconds (3))
                .map (stock -> fakeStockDatasource.randomStocks ());
    }
}

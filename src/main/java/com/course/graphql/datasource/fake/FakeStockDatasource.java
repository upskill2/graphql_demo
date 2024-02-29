package com.course.graphql.datasource.fake;

import com.course.graphql.generated.types.Stock;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.util.List;

@Configuration
public class FakeStockDatasource {

    @Autowired
    private Faker faker;


    public List<Stock> randomStocks () {
        return List.of (
                Stock.newBuilder ()
                        .symbol (faker.stock ().nyseSymbol ())
                        .price (faker.random ().nextInt (100, 1000))
                        .lastTradeDateTIme (OffsetDateTime.now ())
                        .build (),
                Stock.newBuilder ()
                        .symbol (faker.stock ().nyseSymbol ())
                        .price (faker.random ().nextInt (100, 1000))
                        .lastTradeDateTIme (OffsetDateTime.now ())
                        .build (),
                Stock.newBuilder ()
                        .symbol (faker.stock ().nyseSymbol ())
                        .price (faker.random ().nextInt (100, 1000))
                        .lastTradeDateTIme (OffsetDateTime.now ())
                        .build (),
                Stock.newBuilder ()
                        .symbol (faker.stock ().nyseSymbol ())
                        .price (faker.random ().nextInt (100, 1000))
                        .lastTradeDateTIme (OffsetDateTime.now ())
                        .build (),
                Stock.newBuilder ()
                        .symbol (faker.stock ().nyseSymbol ())
                        .price (faker.random ().nextInt (100, 1000))
                        .lastTradeDateTIme (OffsetDateTime.now ())
                        .build ());
    }
}

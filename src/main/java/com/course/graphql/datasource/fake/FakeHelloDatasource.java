package com.course.graphql.datasource.fake;

import com.course.graphql.generated.types.Hello;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FakeHelloDatasource {
    public static final List<Hello> HELLO_LIST = new ArrayList<> ();
    @Autowired
    private Faker faker;

    @PostConstruct
    public void postConstruct () {
        for (int i = 0; i < 20; i++) {
            Hello hello = Hello.newBuilder ()
                    .message (faker.company ().name ())
                    .number (faker.number ().numberBetween (1, 100))
                    .build ();
            HELLO_LIST.add (hello);
        }
    }
}

package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeHelloDatasource;
import com.course.graphql.generated.types.Hello;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@DgsComponent
public class FakeHelloDataResolver {

    @DgsQuery
    public List<Hello> allHellos () {
        return FakeHelloDatasource.HELLO_LIST;
    }

    @DgsQuery
    public Hello hello () {
        return FakeHelloDatasource.HELLO_LIST.get (
                ThreadLocalRandom.current().nextInt(0, FakeHelloDatasource.HELLO_LIST.size())
                );
    }

}

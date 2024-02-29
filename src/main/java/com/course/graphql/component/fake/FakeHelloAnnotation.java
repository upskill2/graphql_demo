package com.course.graphql.component.fake;

import com.course.graphql.generated.DgsConstants;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;

@DgsComponent
public class FakeHelloAnnotation {

    @DgsData (
            parentType = DgsConstants.MUTATION_TYPE)
    public int createHello () {


        return 0;

    }

}

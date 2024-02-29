package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeHelloDatasource;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Hello;
import com.course.graphql.generated.types.HelloInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;

@DgsComponent
public class FakeHelloAnnotation {

    /*    @DgsData (
                parentType = DgsConstants.MUTATION_TYPE,
                field = DgsConstants.MUTATION.CreateHello)*/
    @DgsMutation
    public int createHello (@InputArgument (name = "helloInput") HelloInput helloInput) {
        Hello newHello = Hello.newBuilder ()
                .message (helloInput.getMessage ())
                .number (helloInput.getNumber ())
                .build ();

        FakeHelloDatasource.HELLO_LIST.add (newHello);

        return FakeHelloDatasource.HELLO_LIST.size ();
    }

    @DgsMutation
    public List<Hello> replaceHelloMsg (HelloInput helloInput) {
        FakeHelloDatasource.HELLO_LIST.stream ()
                .filter (hello -> hello.getNumber () == helloInput.getNumber ())
                .forEach (hello -> hello.setMessage (helloInput.getMessage ()));

        return FakeHelloDatasource.HELLO_LIST;
    }

    @DgsMutation
    public int deleteHello (int number) {
        FakeHelloDatasource.HELLO_LIST.removeIf (hello -> hello.getNumber () == number);
        return FakeHelloDatasource.HELLO_LIST.size ();
    }

}

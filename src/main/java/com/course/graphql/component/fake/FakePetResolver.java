package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakePetDatasource;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Cat;
import com.course.graphql.generated.types.Dog;
import com.course.graphql.generated.types.Pet;
import com.course.graphql.generated.types.PetFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;

import java.time.Period;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class FakePetResolver {

    @DgsData (parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Pets)
    public List<Pet> getPets (@InputArgument (name = "petFilter"/*, collectionType = PetFilter.class*/)
                                  Optional<PetFilter> petFilter) {

        if(petFilter.isEmpty ()){
            return FakePetDatasource.PETS_LIST;
        }

        return FakePetDatasource.PETS_LIST.stream ()
                .filter (pet -> this.matchFilter (pet, petFilter.get ()))
                .toList ();

    }

    private boolean matchFilter (final Pet pet, final PetFilter petFilter) {
        if(StringUtils.isBlank (petFilter.getPetType ())){
            return true;
        }

        if(petFilter.getPetType ().equalsIgnoreCase (Dog.class.getSimpleName ())){
            return pet instanceof Dog;
        } else {
            return pet instanceof Cat;
        }

    }
}

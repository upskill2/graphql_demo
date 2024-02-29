package com.course.graphql.datasource.fake;

import com.course.graphql.generated.types.Cat;
import com.course.graphql.generated.types.Dog;
import com.course.graphql.generated.types.Pet;
import com.course.graphql.generated.types.PetFoodType;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FakePetDatasource {

    public static final List<Pet> PETS_LIST = new ArrayList<> ();


    @Autowired
    private Faker faker;

    @PostConstruct
    private void createPetsList () {

        for (int i = 0; i < 10; i++) {
            Pet animal = switch (i % 2) {
                case 0 -> Dog.newBuilder ()
                        .id (faker.animal ().genus ())
                        .name (faker.pokemon ().name ())
                        .foodType (faker.options ().option (PetFoodType.values ()))
                        .barkVolume (faker.number ().numberBetween (1, 10))
                        .size (faker.number ().numberBetween (1, 10))
                        .build ();
                default -> Cat.newBuilder ()
                        .id (faker.animal ().genus ())
                        .name (faker.pokemon ().name ())
                        .foodType (faker.options ().option (PetFoodType.values ()))
                        .meowVolume (faker.number ().numberBetween (1, 10))
                        .size (faker.number ().numberBetween (1, 10))
                        .build ();
            };
            PETS_LIST.add (animal);
        }


    }
}

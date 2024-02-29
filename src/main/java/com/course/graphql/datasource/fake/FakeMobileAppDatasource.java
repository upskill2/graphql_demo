package com.course.graphql.datasource.fake;

import com.course.graphql.generated.types.Address;
import com.course.graphql.generated.types.Author;
import com.course.graphql.generated.types.MobileApp;
import com.course.graphql.generated.types.MobileAppCategory;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FakeMobileAppDatasource {

    public static final List<MobileApp> MOBILE_APP_LIST = new ArrayList<> ();
    @Autowired
    private Faker faker;

    @PostConstruct
    private void buildMobileAppList () throws MalformedURLException {
        for (int i = 0; i < 20; i++) {
            MobileApp mobileApp = MobileApp.newBuilder ()
                    .name (faker.app ().name ())
                    .version (faker.app ().version ())
                    .platform (randomMobileAppPlatform ())
                    .appId (UUID.randomUUID ().toString ())
                    .downloadedTimes (ThreadLocalRandom.current ().nextInt (100, 1000))
                    .releaseDate (LocalDate.now ().minusDays (ThreadLocalRandom.current ().nextInt (1, 100)))
                    .homePage (new URL ("https://" + faker.internet ().url ()))
                    .category (faker.options ().option (MobileAppCategory.class))
                    .author (Author.newBuilder ()
                            .originCountry (faker.country ().name ())
                            .name (faker.artist ().name ())
                            .address (buildAuthorAddress ())
                            .build ())
                    .build ();
            MOBILE_APP_LIST.add (mobileApp);
        }
    }

    private List<Address> buildAuthorAddress () {
        List<Address> addresses = new ArrayList<> ();
        for (int i = 0; i < ThreadLocalRandom.current ().nextInt (1, 4); i++) {
            Address address = Address.newBuilder ()
                    .city (faker.address ().city ())
                    .country (faker.country ().name ())
                    .street (faker.address ().streetAddress ())
                    .zipCode (faker.address ().zipCode ())
                    .build ();
            addresses.add (address);
        }
        return addresses;
    }

    private List<String> randomMobileAppPlatform () {
        switch (ThreadLocalRandom.current ().nextInt (0, 3)) {
            case 0:
                return List.of ("android");
            case 1:
                return List.of ("ios");
            default:
                return List.of ("android", "ios");
        }
    }

}

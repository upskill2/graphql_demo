package com.course.graphql.datasource.fake;

import com.course.graphql.generated.types.Address;
import com.course.graphql.generated.types.Author;
import com.course.graphql.generated.types.Book;
import com.course.graphql.generated.types.ReleaseHistory;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class FakeBookDatasource {

    @Autowired
    private Faker faker;

    private Random random = new Random ();
    public static final List<Book> BOOK_LIST = new ArrayList<> ();

    @PostConstruct
    private void postConstruct () {
        for (int i = 0; i < 20; i++) {
            Address address = Address.newBuilder ()
                    .street (faker.address ().streetAddress ())
                    .city (faker.address ().city ())
                    .country (faker.address ().country ())
                    .build ();

            Author author = Author.newBuilder ()
                    .name (faker.book ().author ())
                    .address (List.of (address))
                    .originCountry (faker.address ().country ())
                    .build ();

            ReleaseHistory releaseHistory = ReleaseHistory.newBuilder ()
                    .year (faker.number ().numberBetween (2019, 2024))
                    .printedEdition (random.nextBoolean () ? true : null)
                    .releaseCountry (random.nextBoolean () ? faker.address ().country () : null)
                    .build ();

            Book book = Book.newBuilder ()
                    .title (faker.book ().title ())
                    .publisher (faker.book ().publisher ())
                    .author (author)
                    .released (releaseHistory)
                    .build ();

            BOOK_LIST.add (book);
        }
    }


}

package com.course.graphql.component.fake;

import com.course.graphql.generated.client.*;
import com.course.graphql.generated.types.Author;
import com.course.graphql.generated.types.Book;
import com.course.graphql.generated.types.ReleaseHistoryInput;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.BaseProjectionNode;
import com.netflix.graphql.dgs.client.codegen.GraphQLQuery;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FakeBookResolverTest {

    @Autowired
    private DgsQueryExecutor queryExecutor;

    @Autowired
    private Faker faker;


    @Test
    void testAllBooks () {

        GraphQLQuery graphQLQuery = new BooksGraphQLQuery
                .Builder ()
                .build ();

        BooksProjectionRoot projectionRoot = (BooksProjectionRoot) new BooksProjectionRoot ()
                .title ()
                .publisher ()
                .author ().name ()
                .originCountry ().getRoot ();

        BooksProjectionRoot projectionRoot1 = (BooksProjectionRoot) new BooksProjectionRoot ()
                .released ()
                .year ()
                .getRoot ();
        final BooksProjectionRoot name = (BooksProjectionRoot) projectionRoot1
                .title ()
                .publisher ()
                .author ().name ()
                .getRoot ();


        String graphqlQueryRequest = new GraphQLQueryRequest (graphQLQuery, projectionRoot)
                .serialize ();
        String graphqlQueryRequest1 = new GraphQLQueryRequest (graphQLQuery, name)
                .serialize ();

        final List<String> titles = queryExecutor.executeAndExtractJsonPath (
                graphqlQueryRequest, "data.books[*].title");

        assertEquals (titles.size (), 20);

        final List<String> authorNames = queryExecutor.executeAndExtractJsonPath (
                graphqlQueryRequest, "data.books[*].author.name");

        assertEquals (titles.size (), 20);

        List<Author> authors = queryExecutor.executeAndExtractJsonPathAsObject (
                graphqlQueryRequest, "data.books[*].author",
                new TypeRef<List<Author>> () {
                }

        );

        assertEquals (authors.size (), 20);

        List<Integer> releasedYears = queryExecutor.executeAndExtractJsonPathAsObject (
                graphqlQueryRequest1, "data.books[*].released.year",
                new TypeRef<List<Integer>> () {
                }

        );

        assertEquals (releasedYears.size (), 20);
    }

    @Test
    void testBooksWithInput () {
        int expectedYears = faker.number ().numberBetween (2019, 2020);
        boolean expectedPrintedEdition = faker.bool ().bool ();

        final ReleaseHistoryInput releaseHistoryInput = ReleaseHistoryInput.newBuilder ()
                .year (expectedYears)
                .printedEdition (expectedPrintedEdition)
                .build ();

        final BooksReleasedHistoryGraphQLQuery graphqlQuery = BooksReleasedHistoryGraphQLQuery
                .newRequest ()
                .releasedInput (releaseHistoryInput)
                .build ();

        final BooksProjectionRoot projectionRoot = (BooksProjectionRoot) new BooksProjectionRoot ()
                .released ()
                .year ()
                .printedEdition ()
                .getRoot ();

        final String graphqlQueryRequest = new GraphQLQueryRequest (graphqlQuery, projectionRoot)
                .serialize ();


        final List<Integer> releasedYears = queryExecutor.executeAndExtractJsonPath (
                graphqlQueryRequest, "data.booksReleasedHistory[*].released.year");

        assertEquals (1, (new HashSet<> (releasedYears)).size ());

    }


}
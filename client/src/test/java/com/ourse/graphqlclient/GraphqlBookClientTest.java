package com.ourse.graphqlclient;


import com.course.graphqlclient.BooksQuery;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphqlBookClientTest {

    private final String SERVER_ENDPOINT = "http://localhost:8080/graphql";

    private final GraphqlBookClient graphqlBookClient = new GraphqlBookClient (SERVER_ENDPOINT);

    @Test
    void allBooks () {
        final List<BooksQuery.Book> books = graphqlBookClient.allBooks ();
        assertTrue (books.size () > 0);
    }

    @Test
    void booksByReleased () {
    }

}
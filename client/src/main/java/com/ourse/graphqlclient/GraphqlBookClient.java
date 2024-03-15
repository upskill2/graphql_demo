package com.ourse.graphqlclient;

import java.awt.print.Book;
import java.util.List;

import com.apollographql.apollo3.ApolloCall;
import com.apollographql.apollo3.ApolloClient;
import com.apollographql.apollo3.api.Executable;
import com.apollographql.apollo3.api.Optional;
import com.apollographql.apollo3.rx3.Rx3Apollo;
import com.course.graphqlclient.BooksQuery;


public class GraphqlBookClient {

    private final ApolloClient apolloClient;

    public GraphqlBookClient (String serverEndpoint) {
        this.apolloClient = new ApolloClient.Builder ().serverUrl (serverEndpoint).build ();
    }

    public List<BooksQuery.Book> allBooks () {
        ApolloCall<? extends BooksQuery.Data> query = apolloClient.query (new BooksQuery ());
        var queryResponse = Rx3Apollo.single (query);

        return queryResponse.blockingGet ().data.books;
    }

/*    public List<BooksByReleased> booksByReleased (int releaseYear, boolean releasePrintedEdition) {
        final ApolloCall<? extends BooksByReleasedQuery.Data> query = apolloClient.query (new BooksByReleasedQuery (Optional.present (releaseYear),
                Optional.present (releasePrintedEdition)));
        var queryResponse = Rx3Apollo.single (query);

        final List<BooksByReleased> booksByReleaseds = queryResponse.blockingGet ().data.booksByReleased;
        return booksByReleaseds;
    }*/

}

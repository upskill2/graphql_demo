package com.ourse.graphqlclient;

import com.apollographql.apollo3.ApolloCall;
import com.apollographql.apollo3.ApolloClient;
import com.course.graphqlclient.BooksQuery;
import com.course.graphqlclient.type.Book;

import java.util.List;

public class GraphqlBookClient {

    private final ApolloClient apolloClient;

    public GraphqlBookClient (String serverEndpoint) {
        this.apolloClient = new ApolloClient.Builder ().serverUrl (serverEndpoint).build ();
    }


    public List<Book> allBooks(){
        final ApolloCall<BooksQuery.Data> query = apolloClient.query (new BooksQuery ());
    }
}

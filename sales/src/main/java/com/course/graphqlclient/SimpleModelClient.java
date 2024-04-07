package com.course.graphqlclient;

import com.netflix.graphql.dgs.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

import java.util.Collections;
import java.util.Map;


@Component
public class SimpleModelClient {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GraphQLClient graphQLClient;

    public GraphQLResponse fetchGraphQLResponse (String graphQLQuery, String operationName,
                                                 Map<String, ? extends Object> variablesMap) {

        if (variablesMap == null) {
            variablesMap = Collections.EMPTY_MAP;
        }

        return graphQLClient.executeQuery (graphQLQuery, variablesMap, operationName, (url, headers, body) -> {
            HttpHeaders httpHeaders = new HttpHeaders ();
            httpHeaders.putAll (headers);

            final ResponseEntity<String> responseEntity = restTemplate.postForEntity (url,
                    new HttpEntity<> (body, httpHeaders), String.class);
            return new HttpResponse (responseEntity.getStatusCodeValue (), responseEntity.getBody ());
        });

    }
}

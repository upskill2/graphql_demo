package com.course.rest;

import com.course.config.GraphQLClientConfig;
import com.course.sales.generated.types.SimpleModel;
import com.netflix.graphql.dgs.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.course.config.GraphQLClientConfig.PRODUCT_URL;

@Component
public class SimpleModelClient {
    @Autowired
    private RestTemplate restTemplate;
/*    @Autowired
    private GraphQLClientConfig graphQLClientConfig;*/

    private GraphQLClient graphQLClient = new DefaultGraphQLClient (PRODUCT_URL);

    public GraphQLResponse fetchGraphQLResponse (String graphQLQuery, String operationName,
                                                 Map<String, ? extends Object> variablesMap) {

        if (variablesMap == null) {
            variablesMap = Collections.EMPTY_MAP;
        }

        final Map<String, ?> finalVariablesMap = variablesMap;
        return graphQLClient.executeQuery (graphQLQuery, variablesMap, operationName, (url, headers, body) -> {
            HttpHeaders httpHeaders = new HttpHeaders ();
            httpHeaders.putAll (headers);

       GraphqlRestRequest graphQLRequest = new GraphqlRestRequest ();
       graphQLRequest.setVariables (finalVariablesMap);
       graphQLRequest.setQuery (graphQLQuery);

            final ResponseEntity<String> responseEntity = restTemplate.postForEntity (url,
                    new HttpEntity<> (body, httpHeaders), String.class);
            return new HttpResponse (responseEntity.getStatusCodeValue (), responseEntity.getBody ());
        });

    }
}

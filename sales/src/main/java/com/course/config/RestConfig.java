package com.course.config;

import com.course.sales.generated.types.SimpleModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.client.CustomGraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLClient;
import com.netflix.graphql.dgs.client.HttpResponse;
import graphql.GraphQL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate () {
        return new RestTemplate ();
    }

    @Bean
    public ObjectMapper objectMapper () {
        return new ObjectMapper ();
    }
}

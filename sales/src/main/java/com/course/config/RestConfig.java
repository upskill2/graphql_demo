package com.course.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.client.DefaultGraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import static com.course.constants.ProductConstants.PRODUCT_URL;

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

    @Bean
    public GraphQLClient graphQLClient () {

        return new DefaultGraphQLClient (PRODUCT_URL);
    }

}

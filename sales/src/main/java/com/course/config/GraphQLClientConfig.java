package com.course.config;

import com.netflix.graphql.dgs.client.CustomGraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLClient;
import com.netflix.graphql.dgs.client.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GraphQLClientConfig {

    public static final String PRODUCT_URL = "http://localhost:8080/graphql";
    @Autowired
    private RestTemplate restTemplate;

  /*  @Bean
    @Primary
    public CustomGraphQLClient graphQLClientBean () {

        return GraphQLClient.createCustom (
                PRODUCT_URL,
                (url, header, body) -> {
                    HttpHeaders headers = new HttpHeaders ();
                    headers.forEach (headers::addAll);

                    final ResponseEntity<String> exchange = restTemplate.exchange (
                            url,
                            HttpMethod.POST,
                            new HttpEntity<> (body, headers),
                            String.class);

                    return new HttpResponse (
                            exchange.getStatusCode ().value (),
                            exchange.getBody ()
                    );
                });
    }*/

}

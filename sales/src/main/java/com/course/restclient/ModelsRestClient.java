package com.course.restclient;

import com.course.sales.generated.types.SimpleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class ModelsRestClient {

    public final static String MODELS_URL = "http://localhost:8080/";

    @Autowired
    private RestTemplate restTemplate;


    public List<SimpleModel> fetchSimpleModels (final List<String> modelUuids) {
        URI uriBuilder = UriComponentsBuilder.fromHttpUrl (
                        MODELS_URL + "api/models/simple").queryParam (
                        "modelUuids", String.join (",", modelUuids))
                .build ()
                .toUri ();

        final ResponseEntity<List<SimpleModel>> responseEntity = restTemplate.exchange (
                uriBuilder, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SimpleModel>> () {
                });

        return responseEntity.getBody ();
    }

}

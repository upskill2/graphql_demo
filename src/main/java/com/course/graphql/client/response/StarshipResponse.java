package com.course.graphql.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
@Getter
@Setter
public class StarshipResponse {

    private String name;
    private String model;
    private List<String> manufacturers;
}

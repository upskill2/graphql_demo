package com.course.graphql.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class FilmResponse {

    private String title;
    private String director;
    private String releaseDate;
}

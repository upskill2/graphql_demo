package com.course.graphql.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
@Getter
@Setter
public class GraphqlErrorResponse {

    @Getter
    @Setter
    public static class Location {
        private int line;
        private int column;

    }

    private String message;
    private List<String> path;
    private List<Location> locations;
}

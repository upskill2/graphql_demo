package com.course.graphql.client;

import com.course.graphql.client.request.GraphqlRestRequest;
import com.course.graphql.client.response.FilmResponse;
import com.course.graphql.client.response.GraphqlErrorResponse;
import com.course.graphql.client.response.PlanetResponse;
import com.course.graphql.client.response.StarshipResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StarWarsClient {

    private static final String URL = "https://swapi-graphql.netlify.app/.netlify/functions/index";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    public String asJson (GraphqlRestRequest body, Map<String, List<String>> headersMap) {
        HttpHeaders httpHeaders = new HttpHeaders ();
        if (headersMap != null) {
            headersMap.forEach (httpHeaders::addAll);
        }

        final ResponseEntity<String> responseEntity = restTemplate.postForEntity (URL,
                new HttpEntity (body, httpHeaders), String.class);

        return responseEntity.getBody ();
    }

    public List<PlanetResponse> getAllPlanets () {
        String query = """
                query allPlanets {
                  allPlanets {
                    planets {
                      name
                      diameter
                      rotationPeriod
                      orbitalPeriod
                      gravity
                      population
                      climates
                      terrains
                    }
                  }
                }
                """;

        GraphqlRestRequest body = new GraphqlRestRequest ();
        body.setQuery (query);

        String Json = asJson (body, null);
        List<PlanetResponse> planetResponses = new ArrayList<> ();

        try {
            final JsonNode jsonNode = objectMapper.readTree (Json);
            final JsonNode data = jsonNode.at ("/data/allPlanets/planets");
            planetResponses = objectMapper.readValue (data.toString (),
                    new TypeReference<List<PlanetResponse>> () {
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException (e);
        }
        return planetResponses;
    }

    public StarshipResponse oneStarShip () {
        String query = """
                query oneStarship {
                  starship(id: "c3RhcnNoaXBzOjU=") {
                    name
                    model
                    starshipClass
                    manufacturersEntity
                    costInCredits
                    length
                    crew
                  }
                }
                """;
        GraphqlRestRequest body = new GraphqlRestRequest ();
        body.setQuery (query);
        String json = asJson (body, null);

        try {
            final JsonNode jsonNode = objectMapper.readTree (json);
            final JsonNode data = jsonNode.at ("/data/starship");
            return objectMapper.readValue (data.toString (), StarshipResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException (e);
        }
    }


    public FilmResponse oneFilm (String filmId) {
        String query = """
                query oneFilm($filmId: ID!) {
                  film(filmID: $filmId) {
                    title
                    episodeID
                    openingCrawl
                    director
                    producers
                    releaseDate
                  }
                }
                """;
        Map<String, String> variables = Map.of ("filmId", filmId);
        GraphqlRestRequest body = new GraphqlRestRequest ();
        body.setQuery (query);
        body.setVariables (variables);
        String json = asJson (body, null);

        try {
            final JsonNode jsonNode = objectMapper.readTree (json);
            final JsonNode data = jsonNode.at ("/data/film");
            return objectMapper.readValue (data.toString (), FilmResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException (e);
        }
    }

    public List<GraphqlErrorResponse> oneFilmError () {
        String query = """
                query oneFilm ($filmId: ID!) {
                  film(filmID: $filmId) {
                    title
                    episodeID
                    openingCrawl
                    director
                    producers
                    releaseDate
                  }
                }
                """;
        Map<String, String> variables = Map.of ("filmId", "invalid");
        GraphqlRestRequest body = new GraphqlRestRequest ();
        body.setQuery (query);
        body.setVariables (variables);
        String json = asJson (body, null);

        try {
            final JsonNode jsonNode = objectMapper.readTree (json);
            final JsonNode errors = jsonNode.at ("/errors");

            if (errors != null && !errors.isEmpty ()) {
                return objectMapper.readValue (errors.toString (),
                        new TypeReference<List<GraphqlErrorResponse>> () {
                        });
            } else {
                return null;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException (e);
        }

    }

}

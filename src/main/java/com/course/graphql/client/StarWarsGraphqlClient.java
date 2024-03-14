package com.course.graphql.client;

import com.course.graphql.client.response.FilmResponse;
import com.course.graphql.client.response.PlanetResponse;
import com.course.graphql.client.response.StarshipResponse;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class StarWarsGraphqlClient {

    private static final String URL = "https://swapi-graphql.netlify.app/.netlify/functions/index";

    private static final String QUERY = """
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
            query oneStarshipFixed {
              starship(id: "c3RhcnNoaXBzOjU=") {
                name
                model
                manufacturers
              }
            }
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

    @Autowired
    RestTemplate restTemplate;
    private GraphQLClient graphQLClient = new DefaultGraphQLClient (URL);

    private GraphQLResponse getGraphqlResponse (String operationName, Map<String, ? extends Object> variablesMap,
                                                Map<String, List<String>> headersMap) {
        if (variablesMap == null) {
            variablesMap = Collections.EMPTY_MAP;
        }

        return graphQLClient.executeQuery (QUERY, variablesMap, operationName, (url, headers, body) -> {
            HttpHeaders httpHeaders = new HttpHeaders ();
            httpHeaders.putAll (headers);

            if (headersMap != null) {
                headersMap.forEach (httpHeaders::addAll);
            }

            final ResponseEntity<String> responseEntity = restTemplate.postForEntity (url,
                    new HttpEntity<> (body, httpHeaders), String.class);
            return new HttpResponse (responseEntity.getStatusCodeValue (), responseEntity.getBody ());
        });
    }


    public String asJson (String operationName, Map<String, ? extends Object> variablesMap,
                          Map<String, List<String>> headersMap) {
        return getGraphqlResponse (operationName, variablesMap, headersMap).getJson ();

    }

    public List<PlanetResponse> allPlanets () {
        return getGraphqlResponse ("allPlanets", null, null)
                .extractValueAsObject ("allPlanets.planets", new TypeRef<List<PlanetResponse>> () {
                });
    }

    public StarshipResponse oneStarship () {
        return getGraphqlResponse ("oneStarshipFixed", null, null)
                .extractValueAsObject ("starship", StarshipResponse.class);
    }

    public FilmResponse oneFilm (String filmId) {
        return getGraphqlResponse ("oneFilm", Map.of ("filmId", filmId), null)
                .extractValueAsObject ("film", FilmResponse.class);
    }

    public List<GraphQLError> oneFilmWithErrors () {
        return getGraphqlResponse ("oneFilm", Map.of ("filmId", "invalid_id"), null)
                .getErrors ();
    }

}

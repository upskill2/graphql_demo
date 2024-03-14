package com.course.graphql.client;

import com.course.graphql.client.request.GraphqlRestRequest;
import com.course.graphql.client.response.FilmResponse;
import com.course.graphql.client.response.GraphqlErrorResponse;
import com.course.graphql.client.response.PlanetResponse;
import com.course.graphql.client.response.StarshipResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StarWarsClientTest {

    @Autowired
    private StarWarsClient starWarsClient;

    @Test
    void testAsJson () {

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


        String response = starWarsClient.asJson (body, null);
        assertEquals (true, response.contains ("allPlanets"));
    }

    @Test
    void testAsJson_Invalid () {

        String randomStringUtils = RandomStringUtils.randomAlphanumeric (10);

        String invalidQuery = """
                query allPlanets {
                  allPlanets_Invalid {
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
        body.setQuery (invalidQuery);

        assertThrows (HttpClientErrorException.class, () -> starWarsClient.asJson (body, null));
    }

    @Test
    void testGetAllPlanets () {
        final List<PlanetResponse> allPlanets = starWarsClient.getAllPlanets ();
        assertNotNull (allPlanets);
        assertFalse (allPlanets.isEmpty ());
    }

    @Test
    void testOneStarShip () {
        final StarshipResponse starship = starWarsClient.oneStarShip ();
        assertNotNull (starship);
        assertFalse (starship.getName ().isEmpty ());
    }

    @Test
    void testOneFilm () {
        final FilmResponse film = starWarsClient.oneFilm ("1");
        assertNotNull (film);
        assertFalse (film.getDirector ().isEmpty ());
        assertFalse (film.getTitle ().isEmpty ());
        assertFalse (film.getReleaseDate ().isEmpty ());
    }

    @Test
    void testOneFilm_Invalid () {
        final List<GraphqlErrorResponse> filmError = starWarsClient.oneFilmError ();
        assertFalse (filmError.isEmpty ());
    }
}
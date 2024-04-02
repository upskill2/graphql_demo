package com.course.graphql.client;

import com.course.graphql.client.response.FilmResponse;
import com.course.graphql.client.response.PlanetResponse;
import com.course.graphql.client.response.StarshipResponse;
import com.netflix.graphql.dgs.client.GraphQLError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StarWarsGraphqlClientTest {

    @Autowired
    private StarWarsGraphqlClient client;

    @Test
    void asJson () {
        final String allPlanets = client.asJson ("allPlanets", null, null);
        assertTrue (allPlanets.contains ("allPlanets"));

    }

    @Test
    void allPlanets () {
        final List<PlanetResponse> planetResponses = client.allPlanets ();
        assertFalse (planetResponses.isEmpty ());
    }

    @Test
    void oneStarship () {
        final StarshipResponse starshipResponse = client.oneStarship ();
        assertNotNull (starshipResponse);
        assertNotNull (starshipResponse.getName ());
        assertNotNull (starshipResponse.getManufacturers ());
        assertNotNull (starshipResponse.getModel ());
    }

    @Test
    void oneFilm () {
        final FilmResponse film = client.oneFilm ("1");
        assertNotNull (film);
        assertNotNull (film.getTitle ());
        assertNotNull (film.getDirector ());
        assertNotNull (film.getReleaseDate ());
    }

    @Test
    void oneFilmWithErrors () {
        final List<GraphQLError> graphQLErrors = client.oneFilmWithErrors ();
        assertFalse (graphQLErrors.isEmpty ());
    }
}
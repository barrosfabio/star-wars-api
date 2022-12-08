package com.space.starwars.integration;

import com.space.starwars.integration.payload.SwapiPlanetResponse;

/**
 * @author Fabio Barros
 * @version 1.0 created on 08/12/2022
 */
public interface SwapiClient {

    SwapiPlanetResponse getPlanetById(String id);

    SwapiPlanetResponse getAllFilms();

}

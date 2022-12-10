package com.space.starwars.model.mapper;

import com.space.starwars.integration.payload.SwapiPlanetResponse;
import com.space.starwars.model.Film;
import com.space.starwars.model.Planet;


import java.util.List;

/**
 * @author Fabio Barros
 * @version 1.0 created on 08/12/2022
 */

public interface SwapiPlanetResponseMapper {

    Planet of(SwapiPlanetResponse swapiPlanetResponse, String id, List<Film> films);
}

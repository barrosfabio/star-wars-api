package com.space.starwars.integration;

import com.space.starwars.integration.payload.SwapiListFilmResponse;
import com.space.starwars.integration.payload.SwapiPlanetResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Fabio Barros
 * @version 1.0 created on 08/12/2022
 */
@FeignClient(value = "swapiClient", url = "https://swapi.dev/api/")
public interface SwapiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/planets/{planetId}")
    SwapiPlanetResponse getPlanetById(@PathVariable("planetId") String id);

    @RequestMapping(method = RequestMethod.GET, value = "/films")
    SwapiListFilmResponse getAllFilms();

}

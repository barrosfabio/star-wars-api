package com.space.starwars.model.mapper.impl;

import com.space.starwars.integration.payload.SwapiPlanetResponse;
import com.space.starwars.model.Film;
import com.space.starwars.model.Planet;
import com.space.starwars.model.mapper.SwapiPlanetResponseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@Mapper
@Component
public class SwapiPlanetResponseMapperImpl implements SwapiPlanetResponseMapper {

    private final static String COMMA_SEPARATOR = ",";
    @Override
    public Planet of(SwapiPlanetResponse swapiPlanetResponse, String id, List<Film> films) {
        if ( swapiPlanetResponse == null && id == null && films == null ) {
            return null;
        }

        Planet.PlanetBuilder planet = Planet.builder();

        if ( swapiPlanetResponse != null ) {
            planet.name( swapiPlanetResponse.getName() );
            planet.climate(csvToList(swapiPlanetResponse.getClimate()));
            planet.terrain(csvToList(swapiPlanetResponse.getTerrain()));
        }
        planet.id( id );
        List<Film> list = films;
        if ( list != null ) {
            planet.films( new ArrayList<Film>( list ) );
        }

        return planet.build();
    }

    private List<String> csvToList(String csvString){
        String[] elements = csvString.trim().split(COMMA_SEPARATOR);

        return Arrays.asList(elements)
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }
}

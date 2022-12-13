package com.space.starwars.model.mapper;

import com.space.starwars.integration.payload.SwapiFilmResponse;
import com.space.starwars.model.Film;

import java.util.List;

/**
 * @author Fabio Barros
 * @version 1.0 created on 08/12/2022
 */
public interface SwapiListFilmResponseMapper {

    List<Film> of(List<SwapiFilmResponse> filmResponseList);
}

package com.space.starwars.model.mapper.impl;

import com.space.starwars.integration.payload.SwapiFilmResponse;
import com.space.starwars.model.Film;
import com.space.starwars.model.mapper.SwapiListFilmResponseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.space.starwars.utils.FilmUtils.getFilmIdFromUrl;

/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@Mapper
@Component
public class SwapiListFilmResponseMapperImpl implements SwapiListFilmResponseMapper {


    @Override
    public Map<String, Film> of(List<SwapiFilmResponse> filmResponseList) {
        if(filmResponseList == null){
            return null;
        }

        Map<String, Film> filmMap = new HashMap<>();

        filmResponseList.forEach(swapiFilmResponse -> filmMap.put(getFilmIdFromUrl(swapiFilmResponse.getUrl()), Film.builder()
                .id(getFilmIdFromUrl(swapiFilmResponse.getUrl()))
                .title(swapiFilmResponse.getTitle())
                .director(swapiFilmResponse.getDirector())
                .releaseDate(swapiFilmResponse.getReleaseDate())
                .url(swapiFilmResponse.getUrl())
                .build())
                                );

        return filmMap;
    }

}

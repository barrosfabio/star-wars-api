package com.space.starwars.model.mapper.impl;

import com.space.starwars.integration.payload.SwapiFilmResponse;
import com.space.starwars.model.Film;
import com.space.starwars.model.mapper.SwapiListFilmResponseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@Mapper
@Component
public class SwapiListFilmResponseMapperImpl implements SwapiListFilmResponseMapper {


    @Override
    public List<Film> of(List<SwapiFilmResponse> filmResponseList) {
        if(filmResponseList == null){
            return null;
        }

        List<Film> filmList = new ArrayList<>();

        filmResponseList.forEach(swapiFilmResponse -> filmList.add(Film.builder()
                .id(getFilmIdFromUrl(swapiFilmResponse.getUrl()))
                .title(swapiFilmResponse.getTitle())
                .director(swapiFilmResponse.getDirector())
                .releaseDate(swapiFilmResponse.getReleaseDate())
                .url(swapiFilmResponse.getUrl())
                .build())
                                );

        return filmList;
    }

    private String getFilmIdFromUrl(String url){
        String [] urlSplitted = url.split("/");

        return urlSplitted[urlSplitted.length - 1];
    }


}

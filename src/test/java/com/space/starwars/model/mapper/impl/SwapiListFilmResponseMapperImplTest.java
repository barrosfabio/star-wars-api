package com.space.starwars.model.mapper.impl;

import com.space.starwars.integration.payload.SwapiFilmResponse;
import com.space.starwars.model.Film;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@ExtendWith(MockitoExtension.class)
class SwapiListFilmResponseMapperImplTest {

    @InjectMocks
    private SwapiListFilmResponseMapperImpl swapiListFilmResponseMapper;

    @Test
    void testValidListFilmResponseMapping(){

       var filmList = swapiListFilmResponseMapper.of(createListSwapiFilmResponse());

       assertEquals(2, filmList.size());
    }

    @Test
    void testNullValidListFilmResponseMapping(){

        List<Film> filmList = swapiListFilmResponseMapper.of(null);

        assertNull(filmList);
    }

    private List<SwapiFilmResponse> createListSwapiFilmResponse(){
        List<SwapiFilmResponse> swapiFilmResponses = new ArrayList<>();
        swapiFilmResponses.add(mockSwapiFilmResponse("https://swapi.dev/api/films/1/"));
        swapiFilmResponses.add(mockSwapiFilmResponse("https://swapi.dev/api/films/2/"));

        return swapiFilmResponses;
    }

    private SwapiFilmResponse mockSwapiFilmResponse(final String url){
        return SwapiFilmResponse.builder()
                .title("A New Hope")
                .director("George Lucas")
                .releaseDate(LocalDate.of(1975,5,25))
                .url(url)
                .episodeId(4)
                .build();
    }
}
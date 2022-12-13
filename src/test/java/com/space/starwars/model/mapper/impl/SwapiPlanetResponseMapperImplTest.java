package com.space.starwars.model.mapper.impl;

import com.space.starwars.integration.payload.SwapiPlanetResponse;
import com.space.starwars.model.Film;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@ExtendWith(MockitoExtension.class)
class SwapiPlanetResponseMapperImplTest {

    private final static String PLANET_ID = "1";

    @InjectMocks
    private SwapiPlanetResponseMapperImpl swapiPlanetResponseMapper;

    @Test
    void testPlanetResponseMapping(){
        var planet = swapiPlanetResponseMapper.of(createMockPlanetResponse(), PLANET_ID, createFilmList());

        assertEquals(2, planet.getFilms().size());
    }

    private SwapiPlanetResponse createMockPlanetResponse(){
        return SwapiPlanetResponse.builder()
                .name("Yavin IV")
                .rotationPeriod("24")
                .orbitalPeriod("4818")
                .diameter("10200")
                .climate("temperate, tropical")
                .gravity("1 standard")
                .terrain("jungle, rainforests")
                .surfaceWater("8")
                .population("1000")
                .films(createFilmUrlList())
                .url("https://swapi.dev/api/planets/3/")
                .build();
    }

    private List<String> createFilmUrlList(){
        List<String> filmList = new ArrayList<>();
        filmList.add("https://swapi.dev/api/films/1/");
        filmList.add("https://swapi.dev/api/films/2/");

        return filmList;
    }

    private List<Film> createFilmList(){
        List<Film> filmList = new ArrayList<>();

        filmList.add(Film.builder()
                .id("1")
                .title("A New Hope")
                .director("George Lucas")
                .releaseDate(LocalDate.of(1975,5,25))
                .url("https://swapi.dev/api/films/1/")
                .build());

        filmList.add(Film.builder()
                .id("2")
                .title("The Empire Strikes Back")
                .director("Irvin Kershner")
                .releaseDate(LocalDate.of(1980,5,17))
                .url("https://swapi.dev/api/films/2/")
                .build());

        return filmList;
    }

}
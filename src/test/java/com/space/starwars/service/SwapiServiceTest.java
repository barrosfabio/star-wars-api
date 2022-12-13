package com.space.starwars.service;

import com.space.starwars.config.RedisCacheConfig;
import com.space.starwars.integration.SwapiClient;
import com.space.starwars.integration.payload.SwapiFilmResponse;
import com.space.starwars.integration.payload.SwapiListFilmResponse;
import com.space.starwars.integration.payload.SwapiPlanetResponse;

import com.space.starwars.model.mapper.impl.SwapiListFilmResponseMapperImpl;
import com.space.starwars.model.mapper.impl.SwapiPlanetResponseMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Fabio Barros
 * @version 1.0 created on 08/12/2022
 */
@ExtendWith(MockitoExtension.class)
class SwapiServiceTest {

    private static final String PLANET_ID = "1";

    @InjectMocks
    private SwapiService swapiService;

    @Mock
    private SwapiClient swapiClient;

    @Mock
    private SwapiListFilmResponseMapperImpl swapiListFilmResponseMapper;

    @Mock
    private SwapiPlanetResponseMapperImpl swapiPlanetResponseMapper;

    @Mock
    private CacheService cacheService;

    @Test
    @DisplayName("Get Planet by ID when all Films not in cache")
    void testGetPlanetByIdWhenAllFilmsNotInCache(){
        when(swapiClient.getPlanetById(PLANET_ID)).thenReturn(createMockPlanetResponse());
        when(swapiClient.getAllFilms()).thenReturn(mockAllFilmsSwapiResponse());
        when(swapiListFilmResponseMapper.of(any(List.class))).thenCallRealMethod();
        when(swapiPlanetResponseMapper.of(any(SwapiPlanetResponse.class), anyString(), any(List.class))).thenCallRealMethod();

        var planet = swapiService.getPlanetById(PLANET_ID);

        assertEquals(2, planet.get().getFilms().size());
        assertNotNull(planet.get());
    }

    @Test
    @DisplayName("Get Planet by ID when all Films in cache")
    void testGetPlanetByIdWhenAllFilmsInCache(){
        when(swapiClient.getPlanetById(PLANET_ID)).thenReturn(createMockPlanetResponse());
        when(cacheService.findCache(RedisCacheConfig.ALL_FILMS_CACHE, SwapiListFilmResponse.class)).thenReturn(mockAllFilmsSwapiResponse());
        when(swapiListFilmResponseMapper.of(any(List.class))).thenCallRealMethod();
        when(swapiPlanetResponseMapper.of(any(SwapiPlanetResponse.class), anyString(), any(List.class))).thenCallRealMethod();

        var planet = swapiService.getPlanetById(PLANET_ID);

        assertEquals(2, planet.get().getFilms().size());
        assertNotNull(planet.get());
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
                .films(createFilmList())
                .url("https://swapi.dev/api/planets/3/")
                .build();
    }

    private List<String> createFilmList(){
        List<String> filmList = new ArrayList<>();
        filmList.add("https://swapi.dev/api/films/1/");
        filmList.add("https://swapi.dev/api/films/2/");

        return filmList;
    }

    private SwapiListFilmResponse mockAllFilmsSwapiResponse(){
        return SwapiListFilmResponse.builder()
                .count(60)
                .results(createListSwapiFilmResponse())
                .build();
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
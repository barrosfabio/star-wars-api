package com.space.starwars.service;

import com.space.starwars.exception.PlanetNotFoundException;
import com.space.starwars.model.Film;
import com.space.starwars.model.Planet;
import com.space.starwars.model.mapper.PagePlanetResponseMapperImpl;
import com.space.starwars.repository.PlanetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@ExtendWith(MockitoExtension.class)
class PlanetServiceTest {

    private static final String PLANET_ID = "1";
    private static final String PLANET_NAME = "Tatooine";

    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRepository planetRepository;

    @Mock
    private SwapiService swapiService;

    @Mock
    private PagePlanetResponseMapperImpl pagePlanetResponseMapper;

    @Test
    @DisplayName("Load Planet by ID when Planet already exists in DB")
    void testLoadPlanetByIdWhenPlanetExistsInDB(){
        // Given
        when(planetRepository.findPlanetById(PLANET_ID)).thenReturn(Optional.ofNullable(createPlanet(PLANET_ID, PLANET_NAME)));

        // When
        var planet = planetService.loadPlanetById(PLANET_ID);

        // Then
        assertEquals(PLANET_ID, planet.getId());
        assertEquals(PLANET_NAME, planet.getName());
        assertEquals(2, planet.getFilms().size());
    }

    @Test
    @DisplayName("Load Planet by ID when Planet does not exist in DB")
    void testLoadPlanetByIdWhenPlanetDoesNotExistInDB(){
        var createdPlanet = createPlanet(PLANET_ID, PLANET_NAME);
        when(planetRepository.findPlanetById(PLANET_ID)).thenReturn(Optional.empty());
        when(swapiService.getPlanetById(PLANET_ID)).thenReturn(Optional.ofNullable(createdPlanet));
        when(planetRepository.save(any(Planet.class))).thenReturn(createdPlanet);

        var planet = planetService.loadPlanetById(PLANET_ID);

        assertEquals(PLANET_ID, planet.getId());
        assertEquals(PLANET_NAME, planet.getName());
        assertEquals(2, planet.getFilms().size());
        verify(planetRepository, times(1)).save(any(Planet.class));
    }


    @Test
    @DisplayName("Get Planet by name when Planet exists in DB")
    void testGetPlanetByNameWhenPlanetExistsInDB() throws Exception {
        when(planetRepository.findPlanetByName(PLANET_NAME)).thenReturn(Optional.ofNullable(createPlanet(PLANET_ID, PLANET_NAME)));

        var planet = planetService.getPlanetByName(PLANET_NAME);

        assertEquals(PLANET_ID, planet.getId());
        assertEquals(PLANET_NAME, planet.getName());
        assertEquals(2, planet.getFilms().size());
    }

    @Test
    @DisplayName("Get Planet by name when Planet does not exist in DB")
    void testGetPlanetByNameWhenPlanetDoesNotExistInDB() {
        var expectedMessage = PlanetNotFoundException.DEFAULT_MESSAGE;
        when(planetRepository.findPlanetByName(PLANET_NAME)).thenReturn(Optional.empty());

        var exception = assertThrows(PlanetNotFoundException.class, () -> {
            planetService.getPlanetByName(PLANET_NAME);
                });

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    @DisplayName("Get Planet by ID when Planet exists in DB")
    void testGetPlanetByIDWhenPlanetExistsInDB() {
        when(planetRepository.findPlanetById(PLANET_ID)).thenReturn(Optional.ofNullable(createPlanet(PLANET_ID, PLANET_NAME)));

        var planet = planetService.getPlanetById(PLANET_ID);

        assertEquals(PLANET_ID, planet.getId());
        assertEquals(PLANET_NAME, planet.getName());
        assertEquals(2, planet.getFilms().size());
    }

    @Test
    @DisplayName("Get Planet by ID when Planet does not exist in DB")
    void testGetPlanetByIDWhenPlanetDoesNotExistInDB() {
        var expectedMessage = PlanetNotFoundException.DEFAULT_MESSAGE;
        when(planetRepository.findPlanetById(PLANET_ID)).thenReturn(Optional.empty());

        var exception = assertThrows(PlanetNotFoundException.class, () -> {
            planetService.getPlanetById(PLANET_ID);
        });

        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    @DisplayName("List Planets when Planets exist in DB")
    void testListPlanetsWhenPlanetsExistInDB() throws Exception {
        var page = 1;
        var pageSize = 5;
        var pageable = PageRequest.of(page, pageSize);
        when(planetRepository.findAll(pageable)).thenReturn(createPlanetPage());
        when(pagePlanetResponseMapper.of(any(List.class), anyBoolean(), any(Integer.class))).thenCallRealMethod();

        var pagePlanetResponse = planetService.findAllPlanets(page, pageSize);

        assertEquals(2, pagePlanetResponse.getPlanets().size());
        assertFalse(pagePlanetResponse.isHasNext());
        assertNull(pagePlanetResponse.getNextPage());
    }


    @Test
    @DisplayName("Delete Planet by ID when Planet exists in DB")
    void testDeletePlanetByIDWhenPlanetExistsInDB() throws Exception {
        planetService.deletePlanetById(PLANET_ID);

        verify(planetRepository, times(1)).deleteById(PLANET_ID);
    }

    private Page<Planet> createPlanetPage(){
        List<Planet> planetList = createPlanetList();
        return new PageImpl<>(planetList);
    }

    private List<Planet> createPlanetList(){
        List<Planet> planetList = new ArrayList<>();
        planetList.add(createPlanet(PLANET_ID, PLANET_NAME));
        planetList.add(createPlanet(PLANET_ID, PLANET_NAME));

        return planetList;
    }

    private Planet createPlanet(final String planetId, final String planetName){
        return Planet.builder()
                .id(planetId)
                .name(planetName)
                .climate(List.of("arid"))
                .terrain(List.of("desert"))
                .films(createFilmList())
                .build();
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
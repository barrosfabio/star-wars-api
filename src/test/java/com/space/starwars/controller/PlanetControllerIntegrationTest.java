package com.space.starwars.controller;

import com.space.starwars.StarWarsApplication;
import com.space.starwars.exception.NotFoundException;
import com.space.starwars.integration.SwapiClient;
import com.space.starwars.integration.payload.SwapiFilmResponse;
import com.space.starwars.integration.payload.SwapiListFilmResponse;
import com.space.starwars.integration.payload.SwapiPlanetResponse;
import com.space.starwars.model.Film;
import com.space.starwars.model.Planet;
import com.space.starwars.repository.PlanetRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Fabio Barros
 * @version 1.0 created on 11/12/2022
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = StarWarsApplication.class
)
@ContextConfiguration(classes = {StarWarsApplication.class})
@AutoConfigureMockMvc
class PlanetControllerIntegrationTest {

    private static final String PLANET_NAME = "Tatooine";
    private static final String PLANET_ID = "1";
    private static final String PLANETS_RESOURCE_URL = "/planets";
    private static final Integer PAGE_NUMBER = 0;
    private static final Integer PAGE_SIZE = 5;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanetRepository planetRepository;

    @MockBean
    private SwapiClient swapiClient;

    @BeforeEach
    public void setup(){
        planetRepository.deleteAll();
    }

    @Test
    @DisplayName("Load Planet by ID when planet does not exist in DB")
    void testLoadPlanetByIdWhenPlanetDoesNotExistInDB() throws Exception {
        given(swapiClient.getAllFilms()).willReturn(mockAllFilmsSwapiResponse());
        given(swapiClient.getPlanetById(PLANET_ID)).willReturn(createMockPlanetResponse());

        mockMvc.perform(post(PLANETS_RESOURCE_URL + "/" + PLANET_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.id", Matchers.is(PLANET_ID)));

    }

    @Test
    @DisplayName("Load Planet by ID when SWAPI integration throws exception")
    void testLoadPlanetByIdWhenSwapiIntegrationThrowsException() throws Exception {
        given(swapiClient.getAllFilms()).willReturn(mockAllFilmsSwapiResponse());
        given(swapiClient.getPlanetById(PLANET_ID)).willThrow(NotFoundException.class);

        mockMvc.perform(post(PLANETS_RESOURCE_URL + "/" + PLANET_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.code", Matchers.is(HttpStatus.NOT_FOUND.value())));

    }

    @Test
    @DisplayName("Get Planet by name")
    void testGetPlanetByName() throws Exception {

        planetRepository.save(createPlanet(PLANET_ID, PLANET_NAME));

        mockMvc.perform(get(PLANETS_RESOURCE_URL)
                        .param("planetName", PLANET_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name", Matchers.is(PLANET_NAME)));

    }

    @Test
    @DisplayName("Get Planet by name then throw PlanetNotFoundException")
    void testGetPlanetByNameThenThrowPlanetNotFoundException() throws Exception {

        mockMvc.perform(get(PLANETS_RESOURCE_URL)
                        .param("planetName", PLANET_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.code", Matchers.is(HttpStatus.NOT_FOUND.value())));

    }

    @Test
    @DisplayName("Get Planet by ID")
    void testGetPlanetById() throws Exception {

        planetRepository.save(createPlanet(PLANET_ID, PLANET_NAME));

        mockMvc.perform(get(PLANETS_RESOURCE_URL + "/" + PLANET_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.id", Matchers.is(PLANET_ID)));

    }

    @Test
    @DisplayName("Get Planet by ID then throw PlanetNotFoundException")
    void testGetPlanetByIdThenThrowPlanetNotFoundException() throws Exception {

        mockMvc.perform(get(PLANETS_RESOURCE_URL + "/" + PLANET_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.code", Matchers.is(HttpStatus.NOT_FOUND.value())));

    }

    @Test
    @DisplayName("List all Planets")
    void testListAllPlanets() throws Exception {
        planetRepository.save(createPlanet("1", "Tatooine"));
        planetRepository.save(createPlanet("2", "Alderaan"));

        mockMvc.perform(get(PLANETS_RESOURCE_URL + "/list")
                        .param("page", String.valueOf(PAGE_NUMBER))
                        .param("pageSize", String.valueOf(PAGE_SIZE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.planets", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.nextPage", Matchers.nullValue()))
                .andExpect(jsonPath("$.hasNext", Matchers.is(false)));

    }

    @Test
    @DisplayName("Delete Planet by ID")
    void testDeletePlanetById() throws Exception {

        mockMvc.perform(delete(PLANETS_RESOURCE_URL + "/" + PLANET_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

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

    private Planet createPlanet(final String planetId, final String planetName) {
        return Planet.builder()
                .id(planetId)
                .name(planetName)
                .climate(List.of("arid"))
                .terrain(List.of("desert"))
                .films(createFilmList())
                .build();
    }

    private List<Film> createFilmList() {
        List<Film> filmList = new ArrayList<>();

        filmList.add(Film.builder()
                .id("1")
                .title("A New Hope")
                .director("George Lucas")
                .releaseDate(LocalDate.of(1975, 5, 25))
                .url("https://swapi.dev/api/films/1/")
                .build());

        filmList.add(Film.builder()
                .id("2")
                .title("The Empire Strikes Back")
                .director("Irvin Kershner")
                .releaseDate(LocalDate.of(1980, 5, 17))
                .url("https://swapi.dev/api/films/2/")
                .build());

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

package com.space.starwars.service;

import com.space.starwars.integration.SwapiClient;
import com.space.starwars.model.mapper.SwapiListFilmResponseMapper;
import com.space.starwars.model.Film;
import com.space.starwars.model.Planet;
import com.space.starwars.model.mapper.SwapiPlanetResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Fabio Barros
 * @version 1.0 created on 08/12/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SwapiService {
    private final SwapiClient swapiClient;
    private final SwapiListFilmResponseMapper swapiListFilmResponseMapper;
    private final SwapiPlanetResponseMapper swapiPlanetResponseMapper;

    public Optional<Planet> getPlanetById(final String planetId){
        log.info("Retrieving Star Wars planet with id=" + planetId);
        var swapiPlanetResponse = swapiClient.getPlanetById(planetId);
        var filteredFilmList = filterFilmsByPlanet(swapiPlanetResponse.getFilms());

        return Optional.ofNullable(swapiPlanetResponseMapper.of(swapiPlanetResponse, planetId, filteredFilmList));
    }

    public List<Film> getAllFilms(){
        log.info("Retrieving all Star Wars films...");
        return swapiListFilmResponseMapper.of(swapiClient.getAllFilms().getResults());
    }

    private List<Film> filterFilmsByPlanet(final List<String> planetFilmList) {
        var allFilms = getAllFilms();
        return allFilms.stream()
                .filter(film ->
                        planetFilmList.stream()
                                .anyMatch(planetFilm ->
                                        planetFilm.equals(film.getUrl())
                                )
                ).collect(Collectors.toList());
    }

}

package com.space.starwars.service;

import com.space.starwars.integration.SwapiClient;
import com.space.starwars.integration.payload.SwapiListFilmResponse;
import com.space.starwars.model.mapper.SwapiListFilmResponseMapper;
import com.space.starwars.model.Film;
import com.space.starwars.model.Planet;
import com.space.starwars.model.mapper.SwapiPlanetResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.space.starwars.config.RedisCacheConfig.ALL_FILMS_CACHE;

/**
 * @author Fabio Barros
 * @version 1.0 created on 08/12/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SwapiService {

    @Value("#{T(java.time.Duration).parse('pt' + '${cache.ttl:1h}')}")
    private Duration cacheTtl;
    private final SwapiClient swapiClient;
    private final SwapiListFilmResponseMapper swapiListFilmResponseMapper;
    private final SwapiPlanetResponseMapper swapiPlanetResponseMapper;

    private final CacheService cacheService;

    public Optional<Planet> getPlanetById(final String planetId) {
        log.info("Retrieving Star Wars planet from SWAPI Public API with id={}",planetId);

        var swapiPlanetResponse = swapiClient.getPlanetById(planetId);
        var filteredFilmList = filterFilmsByPlanet(swapiPlanetResponse.getFilms());

        return Optional.ofNullable(swapiPlanetResponseMapper.of(swapiPlanetResponse, planetId, filteredFilmList));

    }

    /**
     * Retrieve a list of all Star Wars films from the SWAPI public API
     * @return a list with details of all Star Wars films
     */
    public List<Film> getAllFilms(){
        log.info("Retrieving all Star Wars films from the SWAPI Public API...");

        var allFilmsFromCache= cacheService.findCache(ALL_FILMS_CACHE, SwapiListFilmResponse.class);

        if(Objects.nonNull(allFilmsFromCache)){
            return swapiListFilmResponseMapper.of(allFilmsFromCache.getResults());
        } else {
            var allFilmsFromSwapi = swapiClient.getAllFilms();
            cacheService.updateCache(ALL_FILMS_CACHE, allFilmsFromSwapi, cacheTtl);
            return swapiListFilmResponseMapper.of(allFilmsFromSwapi.getResults());
        }

    }

    /**
     *  Method used to filter which of the Star Wars films were filmed in a specific planet
     *
     * @param planetFilmList: list of films that were filmed in a specific planet
     * @return Films filmed in a specific planet
     */
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

package com.space.starwars.service;

import com.space.starwars.exception.PlanetNotFoundException;
import com.space.starwars.model.Planet;
import com.space.starwars.model.mapper.PagePlanetResponseMapper;
import com.space.starwars.model.payload.PagePlanetResponse;
import com.space.starwars.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlanetService {

    private final SwapiService swapiService;

    private final PlanetRepository planetRepository;

    private final PagePlanetResponseMapper pagePlanetResponseMapper;

    public Planet loadPlanetById(final String planetId) {
        return planetRepository.findPlanetById(planetId)
            .orElseGet(() -> {
                log.info("Loading planet_id={} to the database...", planetId);
                return planetRepository.save(swapiService.getPlanetById(planetId).get());
            });

    }

    public Planet getPlanetByName(final String planetName) throws PlanetNotFoundException {
        log.info("Retrieving Planet with name={}", planetName);
        return planetRepository.findPlanetByName(planetName)
                .orElseThrow(PlanetNotFoundException::new);

    }

    public Planet getPlanetById(final String planetId) throws PlanetNotFoundException {
        log.info("Retrieving Planet with id={} ", planetId);
        return planetRepository.findPlanetById(planetId)
                .orElseThrow(PlanetNotFoundException::new);
    }

    public PagePlanetResponse findAllPlanets(final Integer page, final Integer pageSize) {
        log.info("Retrieving page={} with a pageSize={} of the Planets list", page, pageSize);

        Page<Planet> planetPage = planetRepository.findAll(PageRequest.of(page, pageSize));
        return pagePlanetResponseMapper.of(planetPage.getContent(), planetPage.hasNext(), planetPage.getNumber() + 1);
    }

    public void deletePlanetById(final String planetId) {
        log.info("Deleting Planet with id={}", planetId);
        planetRepository.deleteById(planetId);
    }

}

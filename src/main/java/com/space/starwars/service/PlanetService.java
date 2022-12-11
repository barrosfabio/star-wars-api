package com.space.starwars.service;

import com.space.starwars.model.Planet;
import com.space.starwars.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlanetService {

    private static final Integer PAGE_SIZE = 5;

    private final SwapiService swapiService;

    private final PlanetRepository planetRepository;

    public Planet loadPlanetById(final String planetId){
        var planet = planetRepository.findPlanetById(planetId);

        if(planet.isPresent()){
            return planet.get();
        } else {
            log.info("Loading planet with id={} into the database", planetId);
            return planetRepository.save(swapiService.getPlanetById(planetId).get());
        }

    }

    public Planet getPlanetByName(final String planetName) throws Exception {
        return planetRepository.findPlanetByName(planetName)
                .orElseThrow(() -> new Exception("Planet not found"));

    }

    public Planet getPlanetById(final String planetId) throws Exception{
        return planetRepository.findPlanetById(planetId)
                .orElseThrow(() -> new Exception("Planet not found"));
    }

    public Page<Planet> findAllPlanets(final Integer page){
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        return planetRepository.findAll(pageable);
    }

    public void deletePlanetById(final String planetId){
        planetRepository.deleteById(planetId);
    }

}

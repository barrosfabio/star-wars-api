package com.space.starwars.controller;

import com.space.starwars.model.Planet;
import com.space.starwars.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@RestController
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService planetService;

    @PostMapping("/planets/{id}")
    public Planet loadPlanetById(@PathVariable("id") String planetId){
        return planetService.loadPlanetById(planetId);
    }

    @GetMapping("/planets/list")
    public List<Planet> getAllPlanets(){
        return planetService.findAllPlanets();
    }

    @GetMapping("/planets")
    public Planet getPlanetByName(@RequestParam(name = "planetName") String planetName) throws Exception {
        return planetService.getPlanetByName(planetName);
    }

    @GetMapping("/planets/{id}")
    public Planet getPlanetById(@PathVariable("id") String planetId) throws Exception{
        return planetService.getPlanetById(planetId);
    }

    @DeleteMapping("/planets/{id}")
    public void deletePlanetById(@PathVariable("id") String planetId){
        planetService.deletePlanetById(planetId);
    }

}

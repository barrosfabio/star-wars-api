package com.space.starwars.controller;

import com.space.starwars.model.Planet;
import com.space.starwars.model.payload.PagePlanetResponse;
import com.space.starwars.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/planets")
public class PlanetController {

    private final PlanetService planetService;

    @PostMapping("/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Planet loadPlanetById(@PathVariable("id") String planetId){
        return planetService.loadPlanetById(planetId);
    }

    @GetMapping("/list")
    public PagePlanetResponse getAllPlanets(@RequestParam(name = "page") Integer page, @RequestParam(name = "pageSize") Integer pageSize){
        return planetService.findAllPlanets(page, pageSize);
    }

    @GetMapping
    public Planet getPlanetByName(@RequestParam(name = "planetName") String planetName) {
        return planetService.getPlanetByName(planetName);
    }

    @GetMapping("/{id}")
    public Planet getPlanetById(@PathVariable("id") String planetId) {
        return planetService.getPlanetById(planetId);
    }

    @DeleteMapping("/{id}")
    public void deletePlanetById(@PathVariable("id") String planetId){
        planetService.deletePlanetById(planetId);
    }

}

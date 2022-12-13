package com.space.starwars.repository;

import com.space.starwars.model.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> {

    Optional<Planet> findPlanetById(String id);

    Optional<Planet> findPlanetByName(String name);

    void deleteById(String id);
}

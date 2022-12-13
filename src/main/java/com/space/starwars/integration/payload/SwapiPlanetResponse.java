package com.space.starwars.integration.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Fabio Barros
 * @version 1.0 created on 08/12/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwapiPlanetResponse {
    private String name;
    @JsonProperty("rotation_period")
    private String rotationPeriod;

    @JsonProperty("orbital_period")
    private String orbitalPeriod;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;

    @JsonProperty("surface_water")
    private String surfaceWater;
    private String population;
    private List<String> residents;
    private List<String> films;
    private ZonedDateTime created;
    private ZonedDateTime edited;
    private String url;
}

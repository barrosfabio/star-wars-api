package com.space.starwars.integration.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Fabio Barros
 * @version 1.0 created on 08/12/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwapiFilmResponse {
    private String title;

    @JsonProperty("episode_id")
    private Integer episodeId;

    @JsonProperty("opening_crawl")
    private String openingCrawl;
    private String director;
    private String producer;

    @JsonProperty("release_date")
    private LocalDate releaseDate;
    private List<String> characters;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
    private ZonedDateTime created;
    private ZonedDateTime edited;
    private String url;
}

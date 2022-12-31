package com.space.starwars.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Fabio Barros
 * @version 1.0 created on 07/12/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {

    @Schema(description = "ID of the Film", example = "1")
    private String id;
    @Schema(description = "Title of the Film", example = "Revenge of the Sith")
    private String title;
    @Schema(description = "Director of the Film", example = "George Lucas")
    private String director;
    @Schema(description = "The date that the Film was released", example = "12-12-1982")
    private LocalDate releaseDate;
    @Schema(description = "URL of this film in the SWAPI Public API", example = "https://swapi.dev/api/films/1/")
    private String url;

}

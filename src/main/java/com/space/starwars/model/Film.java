package com.space.starwars.model;

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

    private String id;
    private String title;
    private String director;
    private LocalDate releaseDate;

}

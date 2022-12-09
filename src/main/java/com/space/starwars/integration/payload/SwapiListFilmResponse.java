package com.space.starwars.integration.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Fabio Barros
 * @version 1.0 created on 08/12/2022
 */
@Data
@Builder
public class SwapiListFilmResponse {
    private Integer count;
    private Integer next;
    private Integer previous;
    private List<SwapiFilmResponse> results;
}

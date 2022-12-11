package com.space.starwars.model.payload;

import com.space.starwars.model.Planet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Fabio Barros
 * @version 1.0 created on 11/12/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagePlanetResponse {
    private List<Planet> planets;
    private Integer nextPage;
    private boolean hasNext;
}

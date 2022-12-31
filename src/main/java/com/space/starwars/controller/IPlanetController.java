package com.space.starwars.controller;

import com.space.starwars.exception.payload.ErrorResponsePayload;
import com.space.starwars.model.Planet;
import com.space.starwars.model.payload.PagePlanetResponse;
import com.space.starwars.utils.LoggingConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * @author Fabio Barros
 * @version 1.0 27/12/22
 */
public interface IPlanetController {

    @Operation(
        summary = "Loads a Planet from the SWAPI Public API",
        description = "This endpoint retrieves a Planet from the SWAPI Public API, saves it in the database and returns the saved planet in the response.",
        parameters = {
            @Parameter(name = LoggingConstants.PLANET_ID, in = ParameterIn.PATH, description = "Represents the ID of the Planet. It is the same ID used in the SWAPI Public API", required = true)
        }
    )
    @ApiResponse(responseCode = "201", description = "Planet loaded successfully" ,content = @Content(mediaType = "application/json", schema = @Schema(implementation = Planet.class)))
    @ApiResponse(responseCode = "404", description = "Planet Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponsePayload.class)))
    Planet loadPlanetById( String planetId);

    PagePlanetResponse getAllPlanets(Integer page, Integer pageSize);

    Planet getPlanetByName(String planetName);

    Planet getPlanetById(String planetId);

    void deletePlanetById(String planetId);

}

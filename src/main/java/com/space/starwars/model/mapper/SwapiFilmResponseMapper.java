package com.space.starwars.model.mapper;

import com.space.starwars.integration.payload.SwapiFilmResponse;
import com.space.starwars.model.Film;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author Fabio Barros
 * @version 1.0 created on 08/12/2022
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SwapiFilmResponseMapper {

    Film of(SwapiFilmResponse swapiFilmResponse);
}

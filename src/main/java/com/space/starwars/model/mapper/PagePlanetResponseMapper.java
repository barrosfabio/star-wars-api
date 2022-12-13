package com.space.starwars.model.mapper;

import com.space.starwars.model.Planet;
import com.space.starwars.model.payload.PagePlanetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Fabio Barros
 * @version 1.0 created on 11/12/2022
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE

        )
public interface PagePlanetResponseMapper {

    @Mapping(target = "hasNext", source = "hasNext")
    @Mapping(target = "planets", source = "planets")
    @Mapping(target = "nextPage", expression = "java(hasNext? nextPage: null)")
    PagePlanetResponse of(List<Planet> planets, boolean hasNext, Integer nextPage);

}

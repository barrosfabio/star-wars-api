package com.space.starwars.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Fabio Barros
 * @version 1.0 created on 07/12/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("planet")
public class Planet {
    @Id
    @Indexed(unique = true)
    @Schema(description = "ID of the Planet", example = "1")
    private String id;
    @Schema(description = "Name of the Planet", example = "Tatooine")
    private String name;
    @Schema(description = "List of possible climates in this Planet", example = "arid, temperate")
    private List<String> climate;
    @Schema(description = "List of possible types of terrain in this Planet", example = "mountain, desert")
    private List<String> terrain;
    @Schema(description = "List of films where this Planet appeared")
    private List<Film> films;
}

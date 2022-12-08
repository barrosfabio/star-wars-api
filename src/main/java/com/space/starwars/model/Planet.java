package com.space.starwars.model;

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
    private String id;
    private String name;
    private String climate;
    private String terrain;
    private List<Film> films;
}

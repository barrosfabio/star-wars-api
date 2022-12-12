package com.space.starwars.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Fabio Barros
 * @version 1.0 created on 11/12/2022
 */
public class PlanetNotFoundException extends NotFoundException{

    public static String DEFAULT_MESSAGE = "The requested Planet was not found";

    public PlanetNotFoundException(){
        super(HttpStatus.NOT_FOUND.value(), DEFAULT_MESSAGE);
    }

}

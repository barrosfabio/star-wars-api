package com.space.starwars.exception;

/**
 * @author Fabio Barros
 * @version 1.0 created on 11/12/2022
 */
public class NotFoundException extends RuntimeException {
    private Integer code;
    private String message;

    public NotFoundException(Integer code, String message){
        super(message);
        this.code = code;
    }
}

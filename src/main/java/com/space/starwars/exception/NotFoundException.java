package com.space.starwars.exception;

/**
 * @author Fabio Barros
 * @version 1.0 created on 11/12/2022
 */
public class NotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "The requested resource was not found";
    private Integer code;
    private String message;

    public NotFoundException(){
        super(DEFAULT_MESSAGE);
    }

    public NotFoundException(Integer code){
        super(DEFAULT_MESSAGE);
        this.code = code;
    }

    public NotFoundException(Integer code, String message){
        super(message);
        this.code = code;
    }
}

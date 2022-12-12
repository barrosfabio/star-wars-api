package com.space.starwars.exception;

import feign.Response;
import feign.codec.ErrorDecoder;


/**
 * @author Fabio Barros
 * @version 1.0 created on 11/12/2022
 */
public class ApiErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch(response.status()){
            case 404:
                return new NotFoundException(response.status(), "The requested resource was not found in the SWAPI public API");
            default:
                return new Exception("Generic Exception");
        }
    }
}

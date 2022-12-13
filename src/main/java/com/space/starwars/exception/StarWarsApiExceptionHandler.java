package com.space.starwars.exception;

import com.space.starwars.exception.payload.ErrorResponsePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Fabio Barros
 * @version 1.0 created on 11/12/2022
 */
@Slf4j
@RestControllerAdvice
public class StarWarsApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponsePayload handleNotFoundException(RuntimeException exception){
        log.warn("Resource not found in the SWAPI public API, exception={}", exception);
        return ErrorResponsePayload.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(value = {PlanetNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponsePayload handlePlanetNotFoundException(RuntimeException exception){
        log.warn("Planet not found: exception={}", exception);
        return ErrorResponsePayload.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();
    }


}

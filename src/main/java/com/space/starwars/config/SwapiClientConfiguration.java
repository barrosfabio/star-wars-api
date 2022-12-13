package com.space.starwars.config;

import com.space.starwars.exception.ApiErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fabio Barros
 * @version 1.0 created on 10/12/2022
 */
@Configuration
public class SwapiClientConfiguration {
    @Bean
    public ErrorDecoder errorDecoder(){
        return new ApiErrorDecoder();
    }
}

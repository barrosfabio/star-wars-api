package com.space.starwars.exception.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fabio Barros
 * @version 1.0 created on 11/12/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponsePayload {
    private String message;
    private Integer code;
}

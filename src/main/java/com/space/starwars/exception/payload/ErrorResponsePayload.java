package com.space.starwars.exception.payload;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Error message")
    private String message;
    @Schema(description = "Error code")
    private Integer code;
}

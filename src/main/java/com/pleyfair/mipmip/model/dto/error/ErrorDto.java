package com.pleyfair.mipmip.model.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder
public class ErrorDto {
    private HttpStatus status;

    private int statusCode;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    private String path;

    private Collection<String> errors;

}

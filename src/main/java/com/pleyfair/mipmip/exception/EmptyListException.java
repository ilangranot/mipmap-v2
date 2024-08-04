package com.pleyfair.mipmip.exception;

import org.springframework.http.HttpStatus;
import java.util.Set;

public class EmptyListException extends RuntimeException {
    private final Set<String> messages;
    private final HttpStatus httpStatus;

    public EmptyListException(String message, HttpStatus httpStatus) {
        super(message);
        this.messages = Set.of("Some Exception", message);
        this.httpStatus = httpStatus;
    }
}

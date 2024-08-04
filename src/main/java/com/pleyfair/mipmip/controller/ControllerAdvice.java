package com.pleyfair.mipmip.controller;

import com.pleyfair.mipmip.exception.EmptyListException;
import com.pleyfair.mipmip.model.dto.error.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException exception,
                                                                   HttpServletRequest request) {
        log.error(exception.getMessage(), exception);
        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getClass().getSimpleName())
                .errors(Collections.singleton(exception.getLocalizedMessage()))
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDto, errorDto.getStatus());
    }

    @ExceptionHandler(value = {EmptyListException.class})
    public ResponseEntity<ErrorDto> handleException(EmptyListException exception,
                                                           HttpServletRequest request) {
        log.error(exception.getMessage(), exception);
        ErrorDto errorDto = ErrorDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getClass().getSimpleName())
                .errors(Collections.singleton(exception.getLocalizedMessage()))
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDto, errorDto.getStatus());
    }

}

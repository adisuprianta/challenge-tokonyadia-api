package com.enigma.challengetokonyadiaapi.controller;

import com.enigma.challengetokonyadiaapi.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> apiError(ResponseStatusException e){
        return ResponseEntity.status(e.getStatus())
                .body(CommonResponse.builder()
                        .statusCode(e.getStatus().value())
                        .message(e.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> pageError(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(e.getMessage())
                        .build()
                );
    }
}

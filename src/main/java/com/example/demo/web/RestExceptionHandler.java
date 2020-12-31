package com.example.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.ResponseEntity.notFound;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = {VehicleNotFoundException.class})
    public ResponseEntity<Void> vehicleNotFound(VehicleNotFoundException ex, WebRequest request) {
        log.debug("handling VehicleNotFoundException...");
        return notFound().build();
    }

//    @ExceptionHandler(value = {InvalidAuthenticationException.class})
//    public ResponseEntity<Void> invalidJwtAuthentication(InvalidAuthenticationException ex, WebRequest request) {
//        log.debug("handling InvalidJwtAuthenticationException...");
//        return status(UNAUTHORIZED).build();
//    }
}

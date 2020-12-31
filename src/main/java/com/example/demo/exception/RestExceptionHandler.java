package com.example.demo.exception;

import com.example.demo.web.VehicleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.notFound;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {VehicleNotFoundException.class})
    public ResponseEntity<Void> vehicleNotFound(VehicleNotFoundException ex, WebRequest request) {
        log.debug("handling VehicleNotFoundException...");
        return notFound().build();
    }

    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<Void> responseStatusException(ResponseStatusException ex, WebRequest request) {
        log.debug("handling ResponseStatusException...");
        return ResponseEntity.status(ex.getStatus()).build();
    }

//    @ExceptionHandler(value = {InvalidAuthenticationException.class})
//    public ResponseEntity<Void> invalidJwtAuthentication(InvalidAuthenticationException ex, WebRequest request) {
//        log.debug("handling InvalidJwtAuthenticationException...");
//        return ResponseEntity.status(UNAUTHORIZED).build();
//    }
}

package com.example.CurrencyExchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(CurrencyNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> userNotFoundException (Exception e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), e.getMessage(), "Currency");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }



}

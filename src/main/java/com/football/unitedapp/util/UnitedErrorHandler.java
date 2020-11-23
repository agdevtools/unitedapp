package com.football.unitedapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class UnitedErrorHandler {

    public static class BadRequestException extends RuntimeException{}

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestException(){
        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        ErrorDetails errorDetails = new ErrorDetails("Bad Request", "Player","Please try again");
        errorDetailsList.add(errorDetails);
        ErrorResponse errorResponse = new ErrorResponse("400", errorDetailsList);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> validationException(ValidationException ex){
        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        errorDetailsList.addAll(ex.error.details);
        ValidationError error = new ValidationError(ex.error.code, ex.error.message, errorDetailsList);
        ErrorResponse errorResponse = new ErrorResponse("400", errorDetailsList);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

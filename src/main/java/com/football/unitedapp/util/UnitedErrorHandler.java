package com.football.unitedapp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Component
@RestControllerAdvice
public class UnitedErrorHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> validationException(ValidationException ex){
        List<ErrorDetails> errorDetailsList = new ArrayList<>(ex.error.details);
        if(ex.status == 409) {
            ErrorResponse errorResponse = new ErrorResponse("409", errorDetailsList);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }
        else if (ex.status == 404) {
            ErrorResponse errorResponse = new ErrorResponse("404", errorDetailsList);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        else {
            ErrorResponse errorResponse = new ErrorResponse("400", errorDetailsList);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableException(HttpMessageNotReadableException ex){
        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        ErrorDetails errorDetails = new ErrorDetails("Invalid Json", "Team Request", "There were errors in your request. Please check and try again.");
        errorDetailsList.add(errorDetails);
        ErrorResponse errorResponse = new ErrorResponse("400", errorDetailsList);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

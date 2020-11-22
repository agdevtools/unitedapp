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
        List<Error> errorList = new ArrayList<>();
        Error error = new Error("Bad Request", "Please try again");
        errorList.add(error);
        ErrorResponse errorResponse = new ErrorResponse("400",errorList);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

package com.football.unitedapp.util;

public class ValidationException extends RuntimeException{
    int status;
    ValidationError error;

    public ValidationException(int status, ValidationError error) {
        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public ValidationError getError() {
        return error;
    }
}

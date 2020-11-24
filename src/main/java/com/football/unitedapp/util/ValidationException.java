package com.football.unitedapp.util;

public class ValidationException extends RuntimeException{
    int status;
    ValidationError error;

    public ValidationException(int status, ValidationError error) {
        this.status = status;
        this.error = error;
    }

    public ValidationException(String message, int status, ValidationError error) {
        super(message);
        this.status = status;
        this.error = error;
    }

    public ValidationException(String message, Throwable cause, int status, ValidationError error) {
        super(message, cause);
        this.status = status;
        this.error = error;
    }

    public ValidationException(Throwable cause, int status, ValidationError error) {
        super(cause);
        this.status = status;
        this.error = error;
    }

    public ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int status, ValidationError error) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ValidationError getError() {
        return error;
    }

    public void setError(ValidationError error) {
        this.error = error;
    }
}

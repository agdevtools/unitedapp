package com.football.unitedapp.util;

import java.util.List;

public class ValidationError {
    String code;
    String message;
    List<ErrorDetails> details;

    public ValidationError(String code, String message, List<ErrorDetails> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }
}

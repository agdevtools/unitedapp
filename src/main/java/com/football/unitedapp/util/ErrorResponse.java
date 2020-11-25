package com.football.unitedapp.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<ErrorDetails> errorDetails;

    public String getStatus() {
        return status;
    }


    public List<ErrorDetails> getErrorDetails() {
        return errorDetails;
    }


    public ErrorResponse(String status, List<ErrorDetails> errorDetails) {
        this.status = status;
        this.errorDetails = errorDetails;
    }
}

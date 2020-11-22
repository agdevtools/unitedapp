package com.football.unitedapp.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.football.unitedapp.repository.TeamEntity;

import java.util.List;

public class ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Error> errorDetails;

    public ErrorResponse(String status, List<Error> errorDetails) {
        this.status = status;
        this.errorDetails = errorDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Error> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(List<Error> errorDetails) {
        this.errorDetails = errorDetails;
    }
}

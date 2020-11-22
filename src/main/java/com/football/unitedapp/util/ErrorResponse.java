package com.football.unitedapp.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.football.unitedapp.repository.TeamEntity;

import java.util.List;

public class ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Error> errorDetails;
}

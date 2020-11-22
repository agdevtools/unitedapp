package com.football.unitedapp.util;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Error {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;
}

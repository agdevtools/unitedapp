package com.football.unitedapp.util;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorDetails {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String target;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;

    public ErrorDetails(String code, String target, String message) {
        this.code = code;
        this.target = target;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getTarget() {
        return target;
    }

    public String getMessage() {
        return message;
    }

}

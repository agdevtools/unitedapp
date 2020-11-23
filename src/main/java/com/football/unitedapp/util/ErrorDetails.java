package com.football.unitedapp.util;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorDetails {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String target;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDetails(String code, String target, String message) {
        this.code = code;
        this.code = target;
        this.message = message;
    }

    public ErrorDetails(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

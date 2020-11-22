package com.football.unitedapp.util;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Error {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

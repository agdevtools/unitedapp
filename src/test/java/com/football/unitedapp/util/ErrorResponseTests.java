package com.football.unitedapp.util;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseTests {

    @Test
    void test_givenError_thenReturnsError() {
        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        ErrorDetails errorDetails = new ErrorDetails("Bad Request", "PlayerId","Player ID already exists.");
        errorDetailsList.add(errorDetails);

        ErrorResponse errorResponse = new ErrorResponse("409", errorDetailsList);

        assertEquals("409", errorResponse.getStatus());
        assertEquals("Bad Request", errorResponse.getErrorDetails().get(0).getCode());
        assertEquals("PlayerId", errorResponse.getErrorDetails().get(0).getTarget());
        assertEquals("Player ID already exists.", errorResponse.getErrorDetails().get(0).getMessage());
    }
}

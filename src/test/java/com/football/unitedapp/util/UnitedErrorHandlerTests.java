package com.football.unitedapp.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class UnitedErrorHandlerTests {

    @Test
    void testErrorHandler_given404Error_thenReturnsCorrectErrorResponse() {

        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        ValidationError validationError = new ValidationError("404", "Error", errorDetailsList);
        ValidationException validationException = new ValidationException(404, validationError);
        UnitedErrorHandler unitedErrorHandler = new UnitedErrorHandler();

        ResponseEntity<ErrorResponse> errorResponse = unitedErrorHandler.validationException(validationException);

        Assertions.assertEquals(validationError.code + " NOT_FOUND", errorResponse.getStatusCode().toString());
        assertEquals(true, errorResponse.getBody().getErrorDetails().isEmpty());
    }

    @Test
    void testErrorHandler_given409Error_thenReturnsCorrectErrorResponse() {

        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        ValidationError validationError = new ValidationError("409", "Error", errorDetailsList);
        ValidationException validationException = new ValidationException(409, validationError);
        UnitedErrorHandler unitedErrorHandler = new UnitedErrorHandler();

        ResponseEntity<ErrorResponse> errorResponse = unitedErrorHandler.validationException(validationException);

        Assertions.assertEquals(validationError.code + " CONFLICT", errorResponse.getStatusCode().toString());
        assertEquals(true, errorResponse.getBody().getErrorDetails().isEmpty());
    }

    @Test
    void testErrorHandler_given400Error_thenReturnsCorrectErrorResponse() {

        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        ValidationError validationError = new ValidationError("400", "Error", errorDetailsList);
        ValidationException validationException = new ValidationException(400, validationError);
        UnitedErrorHandler unitedErrorHandler = new UnitedErrorHandler();

        ResponseEntity<ErrorResponse> errorResponse = unitedErrorHandler.validationException(validationException);

        Assertions.assertEquals(validationError.code + " BAD_REQUEST", errorResponse.getStatusCode().toString());
        assertEquals(true, errorResponse.getBody().getErrorDetails().isEmpty());
    }

    @Test
    void testErrorHandler_givenHttpMessageNotReadableException_thenReturnsCorrectErrorResponse() {

        HttpMessageNotReadableException httpMessageNotReadableException;
        UnitedErrorHandler unitedErrorHandler = new UnitedErrorHandler();
        ResponseEntity<ErrorResponse> errorResponse = unitedErrorHandler.httpMessageNotReadableException(new HttpMessageNotReadableException("TEST"));

        Assertions.assertEquals("400 BAD_REQUEST", errorResponse.getStatusCode().toString());
        Assertions.assertEquals("There were errors in your request. Please check and try again.", errorResponse.getBody().getErrorDetails().get(0).getMessage());
        Assertions.assertEquals("Invalid Json", errorResponse.getBody().getErrorDetails().get(0).getCode());
    }

}

package com.kars.jmock.server.app.web;

import com.kars.jmock.server.app.exception.MockAdminBadRequestException;
import com.kars.jmock.server.app.exception.MockNotFoundFromRequestException;
import com.kars.jmock.server.repository.exception.MockNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MockServerExceptionHandler {

    @ExceptionHandler({MockNotFoundFromRequestException.class, MockNotFoundException.class})
    public ResponseEntity<Void> mockNotFoundFromRequestException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MockAdminBadRequestException.class})
    public ResponseEntity<Void> mockAdminBadRequestException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

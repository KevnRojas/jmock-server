package com.kars.jmock.server.app.exception;

public class MockNotFoundFromRequestException extends RuntimeException {
    public MockNotFoundFromRequestException(String message) {
        super(message);
    }
}

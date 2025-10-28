package org.example.microservicemusic.exception;

public class RequestNotValidException extends RuntimeException {
    public RequestNotValidException(String message) {
        super(message);
    }
}

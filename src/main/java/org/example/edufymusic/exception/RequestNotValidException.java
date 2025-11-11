package org.example.edufymusic.exception;

public class RequestNotValidException extends RuntimeException {
    public RequestNotValidException(String message) {
        super(message);
    }
}

package org.gojek.entities.exception;

public class InvalidCommandParameterException extends RuntimeException {
    public InvalidCommandParameterException(String message) {
        super(message);
    }
}

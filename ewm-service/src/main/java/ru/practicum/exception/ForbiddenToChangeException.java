package ru.practicum.exception;

public class ForbiddenToChangeException extends RuntimeException {
    public ForbiddenToChangeException(String message) {
        super(message);
    }
}

package ru.practicum.exception;

public class NoNeedToConfirmRequestsException extends RuntimeException {
    public NoNeedToConfirmRequestsException(String message) {
        super(message);
    }
}

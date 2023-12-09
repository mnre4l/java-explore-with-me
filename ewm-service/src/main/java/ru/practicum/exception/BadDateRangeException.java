package ru.practicum.exception;

public class BadDateRangeException extends RuntimeException {
    public BadDateRangeException(String message) {
        super(message);
    }
}

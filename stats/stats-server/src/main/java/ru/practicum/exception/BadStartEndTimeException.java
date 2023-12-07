package ru.practicum.exception;

public class BadStartEndTimeException extends RuntimeException {
    public BadStartEndTimeException(String message) {
        super(message);
    }
}

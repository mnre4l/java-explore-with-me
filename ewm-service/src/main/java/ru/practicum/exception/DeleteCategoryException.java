package ru.practicum.exception;

public class DeleteCategoryException extends RuntimeException {
    public DeleteCategoryException(String message) {
        super(message);
    }
}

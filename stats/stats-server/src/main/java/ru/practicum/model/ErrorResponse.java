package ru.practicum.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private String error;
    private StackTraceElement[] stackTraceElements;


    public ErrorResponse(String message) {
        this.error = message;
    }

    public ErrorResponse(String message, StackTraceElement[] stackTrace) {
        this.error = message;
        this.stackTraceElements = stackTrace;
    }
}

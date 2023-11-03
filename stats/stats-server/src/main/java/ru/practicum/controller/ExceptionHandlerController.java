package ru.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.model.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(final Throwable e) {
        log.warn("Error: " + e.getMessage());
        return new ErrorResponse(e.getMessage(), e.getStackTrace());
    }
}

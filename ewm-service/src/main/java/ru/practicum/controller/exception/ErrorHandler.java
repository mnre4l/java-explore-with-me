package ru.practicum.controller.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.api.exception.ApiError;
import ru.practicum.exception.*;

@RestControllerAdvice
public class ErrorHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ApiError handleBadDateRangeException(final DataIntegrityViolationException e) {
        return ApiError.createApiErrorFromException(e, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ApiError handleDeleteCategoryException(final DeleteCategoryException e) {
        return ApiError.createApiErrorFromException(e, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ApiError handleForbiddenToChangeException(final ForbiddenToChangeException e) {
        return ApiError.createApiErrorFromException(e, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler
    public ApiError handleRequestProcessingException(final RequestProcessingException e) {
        return ApiError.createApiErrorFromException(e, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ApiError handleBadDateRangeException(final BadDateRangeException e) {
        return ApiError.createApiErrorFromException(e, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ApiError handleNotFoundException(final NotFoundException e) {
        return ApiError.createApiErrorFromException(e, HttpStatus.NOT_FOUND);
    }
}

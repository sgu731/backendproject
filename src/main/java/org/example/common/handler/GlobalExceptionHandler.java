package org.example.common.handler;

import org.example.common.dto.ErrorResponse;
import org.example.common.exception.InsufficientStockException;
import org.example.common.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFound(
            ProductNotFoundException e) {

        return new ErrorResponse(
                "PRODUCT_NOT_FOUND",
                e.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(
            MethodArgumentNotValidException e) {

        String message =
                Objects.requireNonNull(e.getBindingResult()
                                .getFieldError())
                        .getDefaultMessage();

        return new ErrorResponse(
                "VALIDATION_ERROR",
                message
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDenied(
            AccessDeniedException e) {

        return new ErrorResponse(
                "ACCESS_DENIED",
                "Access denied"
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(
            Exception e) {

        return new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                e.getMessage()
        );
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInsufficientStock(
            InsufficientStockException e) {

        return new ErrorResponse(
                "INSUFFICIENT_STOCK",
                e.getMessage()
        );
    }
}
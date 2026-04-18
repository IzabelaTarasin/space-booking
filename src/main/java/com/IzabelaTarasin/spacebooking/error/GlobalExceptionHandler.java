package com.IzabelaTarasin.spacebooking.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorized(UnauthorizedException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException ex) {
        ApiError body = ex.getFieldErrors().isEmpty()
                ? new ApiError(ex.getCode(), ex.getMessage())
                : new ApiError(ex.getCode(), ex.getMessage(), ex.getFieldErrors());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    //@Valid rzuca wyjątek MethodArgumentNotValidException skoro nie mam obslugi tego wyjatku tutaj to Spring zwroci mi
    //domyslny JSON błędu (inny niz moj format APIError)
    //dlatego pisze metodde na obsluge tego typu MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        List<FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new FieldError(err.getField(), err.getDefaultMessage()))
                .toList();

        ApiError body = new ApiError("VALIDATION_ERROR",
                "Niepoprawne dane wejściowe",
                fieldErrors);

        return ResponseEntity.badRequest().body(body);
        //każde złe DTO z @Valid zwróci 400 :
        //code: VALIDATION_ERROR
        //message: „Niepoprawne dane wejściowe”
        //fieldErrors: lista pól i komunikatów
    }
}
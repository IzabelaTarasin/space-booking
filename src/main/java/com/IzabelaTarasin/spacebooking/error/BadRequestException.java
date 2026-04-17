package com.IzabelaTarasin.spacebooking.error;

import java.util.List;

public class BadRequestException extends ApiException {
    public BadRequestException(String code, String message) {
        super(code, message);
    }
    public BadRequestException(String code, String message, List<FieldError> fieldErrors) {
        super(code, message, fieldErrors);
    }
}
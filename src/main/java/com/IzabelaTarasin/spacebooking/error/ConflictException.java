package com.IzabelaTarasin.spacebooking.error;

import java.util.List;

public class ConflictException extends ApiException{
    public ConflictException(String code, String message){
        super(code, message);
    }
    public ConflictException(String code, String message, List<FieldError> fieldErrors){
        super(code, message, fieldErrors);
    }
}

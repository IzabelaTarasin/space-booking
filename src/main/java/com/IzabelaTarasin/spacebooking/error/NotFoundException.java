package com.IzabelaTarasin.spacebooking.error;

import java.util.List;

public class NotFoundException extends ApiException{
    public NotFoundException(String code, String message){
        super(code, message);
    }
    public NotFoundException(String code, String message, List<FieldError> fieldErrors){
        super(code, message, fieldErrors);
    }
}

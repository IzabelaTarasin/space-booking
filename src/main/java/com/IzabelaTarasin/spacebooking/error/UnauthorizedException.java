package com.IzabelaTarasin.spacebooking.error;

public class UnauthorizedException extends ApiException{  //do zwrocenia wyjaktu 401 Unauthorized przy logowaniu
    public UnauthorizedException(String code, String message) {
        super(code, message);
    }
}

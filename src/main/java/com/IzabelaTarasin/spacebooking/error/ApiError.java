package com.IzabelaTarasin.spacebooking.error;

import java.util.List;

public class ApiError { //DTO odpowiedzi, To jest kształt odpowiedzi API (body JSON), ktory wysyłam w odpowiedzi HTTP

    private String code;
    private String message;
    private List<FieldError> fieldErrors;

    public ApiError(String code, String message){ //Błąd ogólny (bez wskazywania pól), np. “Planeta startowa i docelowa nie mogą być takie same” albo “Nie znaleziono użytkownika”
        this.code = code;
        this.message = message;
    }

    public  ApiError(String code, String message, List<FieldError> fieldErrors){ //  Błąd walidacji pól (chce pokazać które pola są złe), np. email ma zły format, telephoneNumber puste, itp.
        this.code = code;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public List<FieldError> getFieldErrors() { return fieldErrors; }

}

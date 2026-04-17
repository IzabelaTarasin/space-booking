package com.IzabelaTarasin.spacebooking.error;

import java.util.List;

public abstract class ApiException extends RuntimeException {  //abstract wiec nie da sie utowrzyc obiektu
    private final String code; //final bo  chce by po ustawieniu w kontruktorze juz sie nie dał zmienic. wyjątek ma byc niezmienny(immutability)
    private final List<FieldError> fieldErrors;
    //final musi mieć wartość przy każdym tworzeniu obiektu — nawet jeśli dany konstruktor nie ma argumentu fieldErrors,
    // i tak musze ustawić fieldErrors (np. na pustą listę), bo pole jest częścią obiektu
    protected ApiException(String code, String message) { //konstruktor dostępny dla klas dziedziczących i w tym samym pakiecie
        super(message);  //dzieki temu dostep do metody ex.getMessage() z klasy bazowej RuntimeException
        this.code = code;
        this.fieldErrors = List.of(); //wywołujący nie podaje listy — ale obiekt nadal ma pole fieldErrors, skoro pole final to musi byc zainicjowane
    }
    protected ApiException(String code, String message, List<FieldError> fieldErrors){
        super(message);
        this.code = code;
        this.fieldErrors = fieldErrors == null ? List.of() : List.copyOf(fieldErrors); //List.copyOf daje niezmienną listę — spójne z ideą „wyjątek opisuje stan w momencie rzucenia”.
    } //unikniecie nullpoint exception gdyby byla przekazana null (swiaomie w kodzie)
    //String w Javie jest niezmienny — jak przypiszesz this.code = code, nikt z zewnątrz nie „podmieni znaków”
    //w tym samym obiekcie (w przeciwieństwie do listy, którą nadal można .add() po przekazaniu referencji).
    public String getCode() {
        return code;
    }
    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}

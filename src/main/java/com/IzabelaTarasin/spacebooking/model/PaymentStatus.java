package com.IzabelaTarasin.spacebooking.model;

public enum PaymentStatus {
    PENDING, //oczekiwanie na wplate
    SUCCESS, //platnosc zakonczona powodzeniem
    FAILED, //blad platnosci
    CANCELED //anulowana platnosc przez uzytkownika
}

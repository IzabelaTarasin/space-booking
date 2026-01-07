package com.IzabelaTarasin.spacebooking.model;

import jakarta.persistence.Entity; //JPA – Java Persistence API, zestaw narzędzi i reguł ktore pozwalaja aplikacji na polaczenie z
//baza w sposob obiektowy
//mapuje klasy Javy na tabele klasa na tabele, pole na kolumny, obiekt to wiersz
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class Planet {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    private String name;
    private String galaxy;
    private String description;
}

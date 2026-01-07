package com.IzabelaTarasin.spacebooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class Spacecraft {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    private String model_name;
    @Enumerated(EnumType.STRING)
    private SpacecraftStatus status = SpacecraftStatus.ACTIVE;
}

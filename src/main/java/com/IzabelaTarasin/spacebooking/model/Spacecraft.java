package com.IzabelaTarasin.spacebooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Spacecraft {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    private String model_name;
    @Enumerated(EnumType.STRING)
    private SpacecraftStatus status = SpacecraftStatus.ACTIVE;
    @Column(nullable = false)
    private Integer seatCapacity;
}

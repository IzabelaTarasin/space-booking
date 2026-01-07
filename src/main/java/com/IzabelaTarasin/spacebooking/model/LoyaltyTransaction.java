package com.IzabelaTarasin.spacebooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class LoyaltyTransaction {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @ManyToOne // wiele transakcji nalezy do jednego programu lojalnosciowego
    @JoinColumn(name = "loyalty_program_id")
    private LoyaltyProgram loyaltyProgram;

    private Integer pointsEarned;
    private String description;
}

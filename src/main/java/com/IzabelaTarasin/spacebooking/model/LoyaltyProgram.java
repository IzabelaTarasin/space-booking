package com.IzabelaTarasin.spacebooking.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class LoyaltyProgram {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @OneToOne //jeden uzytkownik moze miec jeden program lojalnosciowy
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private Integer totalPoints = 0;

    @Enumerated(EnumType.STRING)
    private TierStatus status = TierStatus.BRONZE;

}

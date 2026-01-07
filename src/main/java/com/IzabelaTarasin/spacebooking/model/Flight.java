package com.IzabelaTarasin.spacebooking.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Flight {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    private String flightNumber;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    @ManyToOne //Wiele lotów może odbywać się do tej samej planety
    @JoinColumn(name = "origin_planet_id", nullable = false)
    private Planet originPlanet;

    @ManyToOne
    @JoinColumn(name = "destination_planet_id", nullable = false)
    private Planet destinationPlanet;

    @ManyToOne //Wiele (Many) lotów do Jednego (One) statku
    @JoinColumn(name = "spacecraft_id", nullable = false)
    private Spacecraft spacecraft;
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal basePrice;  //cena "katalogowa"
    @Enumerated(EnumType.STRING) // Zapisuje nazwę statusu (np. "DEPARTED") zamiast liczby, lepiej string bo jak sie zmini kolejnosc, nazwy to po string widac od razu
    private FlightStatus status = FlightStatus.SCHEDULED; //ustawiam domyslna wartosc
}

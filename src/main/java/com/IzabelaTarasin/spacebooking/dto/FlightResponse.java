package com.IzabelaTarasin.spacebooking.dto;

import com.IzabelaTarasin.spacebooking.model.FlightStatus;
import com.IzabelaTarasin.spacebooking.model.Planet;
import com.IzabelaTarasin.spacebooking.model.Spacecraft;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class FlightResponse {
    private UUID id;
    private String flightNumber;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Planet originPlanet;
    private Planet destinationPlanet;
    private Spacecraft spacecraft;
    private BigDecimal basePrice;  //cena "katalogowa"
    private FlightStatus status;
}

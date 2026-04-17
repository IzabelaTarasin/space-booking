package com.IzabelaTarasin.spacebooking.dto;

import com.IzabelaTarasin.spacebooking.model.Planet;
import com.IzabelaTarasin.spacebooking.model.Spacecraft;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateFlightRequest {
    @NotBlank
    private String flightNumber;
    @NotNull
    private LocalDateTime departureDate;
    @NotNull
    private LocalDateTime arrivalDate;
    @NotNull
    private Planet originPlanet;
    @NotNull
    private Planet destinationPlanet;
    @NotNull
    private Spacecraft spacecraft;
    @NotNull
    private BigDecimal basePrice;  //cena "katalogowa"
}

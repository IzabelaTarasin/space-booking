package com.IzabelaTarasin.spacebooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CreateSpaceFlightBookingRequest {
    @NotNull
    private UUID originPlanetId;
    @NotNull
    private UUID destinationPlanetId;
    @NotNull
    private LocalDateTime preferredDate;
}

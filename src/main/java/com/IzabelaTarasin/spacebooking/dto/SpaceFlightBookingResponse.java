package com.IzabelaTarasin.spacebooking.dto;

import com.IzabelaTarasin.spacebooking.model.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class SpaceFlightBookingResponse {
    private UUID id;
    private UUID userId;
    private UUID flightId;
    private BigDecimal finalPrice;
    private PaymentStatus paymentStatus;
}

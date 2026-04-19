package com.IzabelaTarasin.spacebooking.dto;

import com.IzabelaTarasin.spacebooking.model.SpaceFlightBooking;
import org.springframework.stereotype.Component;

@Component
public class SpaceFlightBookingMapper {
    public SpaceFlightBookingResponse toDTO(SpaceFlightBooking spaceFlightBooking){
        SpaceFlightBookingResponse spaceFlightBookingDTO = new SpaceFlightBookingResponse();
        spaceFlightBookingDTO.setId(spaceFlightBooking.getId());
        spaceFlightBookingDTO.setUserId(spaceFlightBooking.getUser().getId());
        spaceFlightBookingDTO.setFlightId(spaceFlightBooking.getFlight().getId());
        spaceFlightBookingDTO.setFinalPrice(spaceFlightBooking.getFinalPrice());
        spaceFlightBookingDTO.setPaymentStatus(spaceFlightBooking.getPaymentStatus());
        return spaceFlightBookingDTO;
    }
}

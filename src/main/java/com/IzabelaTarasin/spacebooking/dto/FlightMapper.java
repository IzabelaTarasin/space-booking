package com.IzabelaTarasin.spacebooking.dto;

import com.IzabelaTarasin.spacebooking.model.Flight;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class FlightMapper {

    public Flight toEntity(CreateFlightRequest flightRequest){
        Flight flight = new Flight();
        flight.setFlightNumber(flightRequest.getFlightNumber());
        flight.setDepartureDate(flightRequest.getDepartureDate());
        flight.setArrivalDate(flightRequest.getArrivalDate());
        flight.setOriginPlanet(flightRequest.getOriginPlanet());
        flight.setDestinationPlanet(flightRequest.getDestinationPlanet());
        flight.setSpacecraft(flightRequest.getSpacecraft());
        flight.setBasePrice(flightRequest.getBasePrice());

        return flight;
    }

    public Flight toEntity(UpdateFlightRequest flightRequest){
        Flight flight = new Flight();
        flight.setFlightNumber(flightRequest.getFlightNumber());
        flight.setDepartureDate(flightRequest.getDepartureDate());
        flight.setArrivalDate(flightRequest.getArrivalDate());
        flight.setOriginPlanet(flightRequest.getOriginPlanet());
        flight.setDestinationPlanet(flightRequest.getDestinationPlanet());
        flight.setSpacecraft(flightRequest.getSpacecraft());
        flight.setBasePrice(flightRequest.getBasePrice());

        return flight;
    }

    public FlightResponse toDTO(Flight flight){
        FlightResponse flightDTO = new FlightResponse();
        flightDTO.setId(flight.getId());
        flightDTO.setFlightNumber(flight.getFlightNumber());
        flightDTO.setDepartureDate(flight.getDepartureDate());
        flightDTO.setArrivalDate(flight.getArrivalDate());
        flightDTO.setOriginPlanet(flight.getOriginPlanet());
        flightDTO.setDestinationPlanet(flight.getDestinationPlanet());
        flightDTO.setSpacecraft(flight.getSpacecraft());
        flightDTO.setBasePrice(flight.getBasePrice());
        flightDTO.setStatus(flight.getStatus());

        return flightDTO;
    }

    public List<FlightResponse> toFlightResponseList(List<Flight> flights) {
        return flights.stream()
                .map(this::toDTO)
                .toList();
    }
}

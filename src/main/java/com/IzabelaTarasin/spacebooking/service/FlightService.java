package com.IzabelaTarasin.spacebooking.service;

import com.IzabelaTarasin.spacebooking.error.BadRequestException;
import com.IzabelaTarasin.spacebooking.error.ConflictException;
import com.IzabelaTarasin.spacebooking.error.NotFoundException;
import com.IzabelaTarasin.spacebooking.model.Flight;
import com.IzabelaTarasin.spacebooking.model.FlightStatus;
import com.IzabelaTarasin.spacebooking.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository){
        this.flightRepository = flightRepository;
    }

    public Flight createFlight(Flight flight){
        //walidacja musza byc rozne planety odlotu i przylotu
        //if(flight.getOriginPlanet() != flight.getDestinationPlanet()) //spr referencje "czy jest to samo pudełko?
            //czyli moze byc tak ze są dwa obiekty Mars w roznych pamieciach, dostaniemy false a jednak beda te same
            //musimy uzyc equals by porownac zawartosc
        if(flight.getOriginPlanet().equals(flight.getDestinationPlanet())) {
            throw new BadRequestException("ORIGIN_EQUALS_DESTINATION", "Planeta startowa i docelowa nie mogą być takie same!");
        }

        //Walidacja:  data przylotu wczesnijesza niz data odlotu
        if(flight.getArrivalDate().isBefore(flight.getDepartureDate())){
            throw new BadRequestException("ARRIVAL_BEFORE_DEPARTURE","Data przylotu nie może być wcześniejsza niż data odlotu!");
        }

        //Walidacja: Czy numer lotu jest już zajęty?
        if(flightRepository.existsByFlightNumber(flight.getFlightNumber())){
            throw new ConflictException("FLIGHT_NUMBER_TAKEN","Lot o numerze " + flight.getFlightNumber() + " już istnieje");
        }

        return flightRepository.save(flight);
    }

    public Flight updateFlight(UUID id, Flight flight){
        Flight existingFlight = flightRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("FLIGHT_NOT_FOUND",
                        "Nie znaleziono lotu o id: "+ id));

        existingFlight.setFlightNumber(flight.getFlightNumber());
        existingFlight.setBasePrice(flight.getBasePrice());
        existingFlight.setDepartureDate(flight.getDepartureDate());
        existingFlight.setArrivalDate(flight.getArrivalDate());
        existingFlight.setSpacecraft(flight.getSpacecraft());
        existingFlight.setOriginPlanet(flight.getOriginPlanet());
        existingFlight.setDestinationPlanet(flight.getDestinationPlanet());

        return flightRepository.save(existingFlight);
    }

    public void deleteFlight(UUID id){
        if(!flightRepository.existsById(id)){
            throw new NotFoundException("FLIGHT_NOT_FOUND",
                    "Nie można usunąć lotu - nie znaleziono lotu o id: "+ id);
        }
        flightRepository.deleteById(id);
    }

    public List<Flight> getAllFlights(){
        return flightRepository.findAll().stream().sorted().toList();
    }

    public Flight getFlightById(UUID id){
        return flightRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("FLIGHT_NOT_FOUND",
                "Nie znaleziono lotu o id: "+ id));
    }

    public List<Flight> getAllFlightByUserId(UUID userID){
        return flightRepository.findFlightsByUserId(userID);
    }

    public Flight getFlightByNumber(String flightNumber){
        return flightRepository.findByFlightNumber(flightNumber);
    }

    public List<Flight> getFlightsByStatus(FlightStatus flightStatus){
        return flightRepository.findByStatus(flightStatus);
    }

    public List<Flight> getFlightsByDepartureDateAfter(LocalDateTime departureDate){
        return flightRepository.findByDepartureDateAfter(departureDate);
    }

    public  List<Flight> getFlightsByArrivalDateAfter(LocalDateTime arrivalDate){
        return flightRepository.findByArrivalDateAfter(arrivalDate);
    }

    public List<Flight> getCheapFlights(BigDecimal maxPrice){
        return flightRepository.findCheapFlights(maxPrice);
    }
}

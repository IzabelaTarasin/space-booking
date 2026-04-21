package com.IzabelaTarasin.spacebooking;

import com.IzabelaTarasin.spacebooking.error.ConflictException;
import com.IzabelaTarasin.spacebooking.model.Flight;
import com.IzabelaTarasin.spacebooking.model.Planet;
import com.IzabelaTarasin.spacebooking.repository.FlightRepository;
import com.IzabelaTarasin.spacebooking.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class FlightServiceTest {
    @Mock
    private FlightRepository flightRepository;
    @InjectMocks
    private FlightService flightService;

    @Test
    public void createFlight_throwsConflictException_whenFlightNumberAlreadyTaken (){
        Flight flight = new Flight();

        String flightNumber = "SX-1324";
        when(flightRepository.existsByFlightNumber(flightNumber)).thenReturn(true); //symulacja ze juz w bazie jest ten numer

        Planet origin = new Planet();
        origin.setName("Jupiter");

        Planet destination= new Planet();
        destination.setName("Mars");

        flight.setFlightNumber(flightNumber);
        flight.setOriginPlanet(origin);
        flight.setDestinationPlanet(destination);
        flight.setArrivalDate(LocalDateTime.parse("2030-01-02T10:00:00"));
        flight.setDepartureDate(LocalDateTime.parse("2030-01-01T10:00:00"));

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> flightService.createFlight(flight)
        );

        assertEquals("FLIGHT_NUMBER_TAKEN", exception.getCode());
        verify(flightRepository).existsByFlightNumber(flightNumber);
        verify(flightRepository, never()).save(any()); //upewniam sie ze nie doszlo do zapisu
    }
}

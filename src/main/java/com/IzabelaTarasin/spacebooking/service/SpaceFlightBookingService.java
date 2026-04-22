package com.IzabelaTarasin.spacebooking.service;

import com.IzabelaTarasin.spacebooking.dto.SpaceFlightBookingMapper;
import com.IzabelaTarasin.spacebooking.error.BadRequestException;
import com.IzabelaTarasin.spacebooking.error.ConflictException;
import com.IzabelaTarasin.spacebooking.error.NotFoundException;
import com.IzabelaTarasin.spacebooking.model.*;
import com.IzabelaTarasin.spacebooking.repository.FlightRepository;
import com.IzabelaTarasin.spacebooking.repository.SpaceFlightBookingRepository;
import com.IzabelaTarasin.spacebooking.repository.UserRepository;
import com.IzabelaTarasin.spacebooking.util.SumDigitsInDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.IzabelaTarasin.spacebooking.dto.SpaceFlightBookingResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SpaceFlightBookingService {

    //Czy taki użytkownik istnieje?
    //Czy taki lot istnieje?
    //Kluczowe: Czy w statku kosmicznym są jeszcze wolne miejsca?
    //w tym serwisie musi byc dostep do kilku innych repository

    private final SpaceFlightBookingRepository spaceFlightBookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final SpaceFlightBookingMapper spaceFlightBookingMapper;

    public SpaceFlightBookingService(SpaceFlightBookingRepository spaceFlightBookingRepository, UserRepository userRepository, FlightRepository flightRepository, SpaceFlightBookingMapper spaceFlightBookingMapper){
        this.spaceFlightBookingRepository = spaceFlightBookingRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.spaceFlightBookingMapper = spaceFlightBookingMapper;
    }

    private Flight findFlightForBooking(Planet originPlanet, Planet destinationPlanet, LocalDateTime preferredDate){
        // 1. Szukamy lotów na danej trasie
        List<Flight> availableFlights = flightRepository.findByOriginPlanetAndDestinationPlanet(originPlanet, destinationPlanet);
        // 2. Filtrujemy te, które startują po wybranej dacie i mają status SCHEDULED
        return availableFlights.stream()
                .filter(f -> f.getStatus().equals(FlightStatus.SCHEDULED))
                .filter(f -> f.getDepartureDate().isAfter(preferredDate))
                .filter(f -> f.getAvailableSeats() != null && f.getAvailableSeats() > 0)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("FLIGHT_NOT_FOUND",
                        "Nie znaleziono lotu dla trasy "
                                + originPlanet.getName() + " - "
                                + destinationPlanet.getName() + " w terminie "
                                + preferredDate));
    }

    @Transactional
    public SpaceFlightBookingResponse bookFlight(UUID userID, Planet originPlanet, Planet destinationPlanet, LocalDateTime preferredDate){
        // 1. Pobierz usera i lot (lub rzuć błąd)
        User user = userRepository
                .findById(userID)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND","Nie ma takiego użytkownika o id: " + userID));

        if (spaceFlightBookingRepository.existsByUser(user)) {
            throw new ConflictException(
                    "USER_ALREADY_HAS_BOOKING",
                    "Użytkownik może mieć tylko jedną rezerwację lotu.");
        }

        Flight flight = flightRepository
                .findById(findFlightForBooking(originPlanet, destinationPlanet, preferredDate).getId())
                .orElseThrow(() -> new NotFoundException("FLIGHT_NOT_FOUND", "Nie znaleziono lotu"));

        //3. weryfikacja daty, czy nie bookuje z przeszlości
        if(flight.getDepartureDate().isBefore(LocalDateTime.now())){
            throw new BadRequestException("DEPARTURE_IN_PAST", "Nie można zarezerwować lotu z datą przeszłą");
        }

        //2. Logika biznesowa: Sprawdź dostepne miejsca we flight
        Integer seats = flight.getAvailableSeats();
        if (seats == null || seats < 1) {
            throw new ConflictException("NO_SEATS_AVAILABLE", "Brak wolnych miejsc na tym locie.");
        }
        flight.setAvailableSeats(seats - 1);
        flightRepository.save(flight);

        SpaceFlightBooking spaceFlightBooking = new SpaceFlightBooking();
        spaceFlightBooking.setUser(user);
        spaceFlightBooking.setFlight(flight);
        spaceFlightBooking.setPaymentStatus(PaymentStatus.SUCCESS);
        spaceFlightBooking.setFinalPrice(calculatePrice(flight.getDepartureDate()));

        SpaceFlightBooking savedSpaceFlightBooking = spaceFlightBookingRepository.save(spaceFlightBooking);
        return spaceFlightBookingMapper.toDTO(savedSpaceFlightBooking);
    }

    private static BigDecimal calculatePrice(LocalDateTime departureDate){
        int base = SumDigitsInDateTime.sumDigits(departureDate);
        int random = ThreadLocalRandom.current().nextInt(1, 101);
        BigDecimal finalPrice = BigDecimal.valueOf((long) base * random);
        return finalPrice;
    }
}

package com.IzabelaTarasin.spacebooking.service;

import com.IzabelaTarasin.spacebooking.error.BadRequestException;
import com.IzabelaTarasin.spacebooking.error.ConflictException;
import com.IzabelaTarasin.spacebooking.error.NotFoundException;
import com.IzabelaTarasin.spacebooking.model.*;
import com.IzabelaTarasin.spacebooking.repository.FlightRepository;
import com.IzabelaTarasin.spacebooking.repository.SpaceFlightBookingRepository;
import com.IzabelaTarasin.spacebooking.repository.UserRepository;
import org.springframework.stereotype.Service;

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

    public SpaceFlightBookingService(SpaceFlightBookingRepository spaceFlightBookingRepository, UserRepository userRepository, FlightRepository flightRepository){
        this.spaceFlightBookingRepository = spaceFlightBookingRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
    }

    private Flight findFlightForBooking(Planet originPlanet, Planet destinationPlanet, LocalDateTime preferredDate){
        // 1. Szukamy lotów na danej trasie
        List<Flight> availableFlights = flightRepository.findByOriginPlanetAndDestinationPlanet(originPlanet, destinationPlanet);
        // 2. Filtrujemy te, które startują po wybranej dacie i mają status PLANNED
        return availableFlights.stream()
                .filter(f -> f.getStatus().equals(FlightStatus.SCHEDULED))
                .filter(f -> f.getDepartureDate().isAfter(preferredDate))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("FLIGHT_NOT_FOUND",
                        "Nie znaleziono lotu dla trasy "
                                + originPlanet.getName() + " - "
                                + destinationPlanet.getName() + " w terminie "
                                + preferredDate));
    }

    public SpaceFlightBooking bookFlight(UUID userID, Planet originPlanet, Planet destinationPlanet, LocalDateTime preferredDate){
        // 1. Pobierz usera i lot (lub rzuć błąd)
        User user = userRepository
                .findById(userID)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND","Nie ma takiego użytkownika o id: " + userID));
        Flight flight = findFlightForBooking(originPlanet, destinationPlanet, preferredDate);

        // 2. Logika biznesowa: Sprawdź miejsca (jeśli dodasz seatCapacity)
        long currentBookings = spaceFlightBookingRepository.countByFlight_Id(flight.getId());
        if(currentBookings >= flight.getSpacecraft().getSeatCapacity()){
            throw new ConflictException("NO_SEATS_AVAILABLE", "Brak wolnych miejsc w statku!");
        };
        //3. weryfikacja daty, czy nie bookuje z przeszlości
        if(flight.getDepartureDate().isBefore(LocalDateTime.now())){
            throw new BadRequestException("DEPARTURE_IN_PAST", "Nie można zarezerwować lotu z datą przeszłą");
        }

        SpaceFlightBooking spaceFlightBooking = new SpaceFlightBooking();
        spaceFlightBooking.setUser(user);
        spaceFlightBooking.setFlight(flight);
        spaceFlightBooking.setPaymentStatus(PaymentStatus.SUCCESS);
        spaceFlightBooking.setFinalPrice(calculatePrice(flight.getDepartureDate()));

        return spaceFlightBookingRepository.save(spaceFlightBooking);
    }

    private static int sumDigitsInDate(LocalDateTime date) {
        int sum = 0;
        for (char c : date.toString().toCharArray()) {
            if (Character.isDigit(c)) {
                sum += c - '0';
            }
        }
        return sum;
    }

    private static BigDecimal calculatePrice(LocalDateTime departureDate){
        int baza = sumDigitsInDate(departureDate);
        int los = ThreadLocalRandom.current().nextInt(1, 101);
        BigDecimal finalPrice = BigDecimal.valueOf((long) baza * los);
        return finalPrice;
    }
}

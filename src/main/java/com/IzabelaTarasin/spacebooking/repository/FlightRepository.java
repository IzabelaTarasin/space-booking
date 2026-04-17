package com.IzabelaTarasin.spacebooking.repository;

import com.IzabelaTarasin.spacebooking.model.Flight;
import com.IzabelaTarasin.spacebooking.model.FlightStatus;
import com.IzabelaTarasin.spacebooking.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {
    //sprawdzać dostępne kierunki, daty lotów i statusy.

    // 1. Znajdź lot po numerze lotu
    Flight findByFlightNumber(String flightNumber);
    // 2. Znajdź loty do konkretnej planety (przekazujesz obiekt Planet)
    List<Flight> findByDestinationPlanet(Planet planet);
    List<Flight> findByOriginPlanet(Planet planet);
    // 3. Znajdź loty startujące po konkretnej dacie
    List<Flight> findByDepartureDateAfter(LocalDateTime departureDate);
    // 3. Znajdź loty przylatujące po konkretnej dacie
    List<Flight> findByArrivalDateAfter(LocalDateTime arrivalDate);
    // 4. Szukanie bezpośrednie: Skąd -> Dokąd
    List<Flight> findByOriginPlanetAndDestinationPlanet(Planet originPlanet, Planet destinationPlanet);
    // 5. Filtrowanie po statusie (np. tylko zaplanowane loty)
    List<Flight> findByStatus(FlightStatus status);
    @Query("SELECT f FROM Flight f WHERE f.basePrice < :price")
    List<Flight> findCheapFlights(@Param("price") BigDecimal price);
    @Query("SELECT DISTINCT b.flight FROM SpaceFlightBooking b WHERE b.user.id = :userId")
    List<Flight> findFlightsByUserId(@Param("userId") UUID userId);
    boolean existsByFlightNumber(String flightNumber);


}

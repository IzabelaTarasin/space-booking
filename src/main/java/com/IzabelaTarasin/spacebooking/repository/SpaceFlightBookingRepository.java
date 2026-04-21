package com.IzabelaTarasin.spacebooking.repository;

import com.IzabelaTarasin.spacebooking.model.Flight;
import com.IzabelaTarasin.spacebooking.model.PaymentStatus;
import com.IzabelaTarasin.spacebooking.model.SpaceFlightBooking;
import com.IzabelaTarasin.spacebooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpaceFlightBookingRepository extends JpaRepository<SpaceFlightBooking, UUID> {
    //Najważniejsze dla procesu biznesowego.
    // Służy do tworzenia nowych rezerwacji,
    // pobierania historii rezerwacji danego użytkownika czy odwoływania lotów.

    // 1. Znajdź wszystkie rezerwacje konkretnego użytkownika (np. do widoku "Moje Loty")
    List<SpaceFlightBooking> findByUser(User user);
    // 2. Znajdź wszystkie rezerwacje na konkretny lot (żeby sprawdzić listę pasażerów)
    List<SpaceFlightBooking> findByFlight(Flight flight);
    // 3. Znajdź rezerwacje użytkownika o konkretnym statusie płatności
    List<SpaceFlightBooking> findByUserAndPaymentStatus(User user, PaymentStatus paymentStatus);
    // 4. Sprawdź, czy użytkownik już ma rezerwację na ten konkretny lot
    boolean existsByUserAndFlight(User user, Flight flight);

    //5. sprawz ile jest miejsc wolnych
    long countByFlightId(UUID flightID);
}

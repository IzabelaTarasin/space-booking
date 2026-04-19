package com.IzabelaTarasin.spacebooking;

import com.IzabelaTarasin.spacebooking.error.NotFoundException;
import com.IzabelaTarasin.spacebooking.model.*;
import com.IzabelaTarasin.spacebooking.repository.FlightRepository;
import com.IzabelaTarasin.spacebooking.repository.SpaceFlightBookingRepository;
import com.IzabelaTarasin.spacebooking.repository.UserRepository;
import com.IzabelaTarasin.spacebooking.service.SpaceFlightBookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class SpaceFlightBookingServiceTest {
    @Mock
    private SpaceFlightBookingRepository spaceFlightBookingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FlightRepository flightRepository;
    @InjectMocks
    private SpaceFlightBookingService spaceFlightBookingService;

    @Test
    public void bookFlight_throwsUserNotFound_whenUserDoesNotExist(){
        // arrange
        UUID userId = UUID.fromString("a0000001-0000-0000-0000-000000000099");
        when(userRepository.findById(userId)).thenReturn(Optional.empty()); //symulacja ze nie ma uzytkownika z podanym id jak wyzej
        Planet origin = new Planet();
        origin.setName("Uranus");
        Planet destination = new Planet();
        destination.setName("Mars");
        LocalDateTime preferredDate = LocalDateTime.parse("2030-01-01T00:00:00");

        // act
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> spaceFlightBookingService.bookFlight(userId, origin, destination, preferredDate));

        // assert
        assertThat(exception.getCode()).isEqualTo("USER_NOT_FOUND");
        verify(userRepository).findById(userId);
    }

    @Test
    public void bookFlight_shouldSaveBooking_whenValidUserAndFlight(){
        // arrange
        UUID userId = UUID.fromString("a0000001-0000-0000-0000-000000000099");
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Planet origin = new Planet();
        origin.setName("Uranus");
        Planet destination = new Planet();
        destination.setName("Mars");
        LocalDateTime preferredDate = LocalDateTime.parse("2030-01-01T00:00:00");

        Spacecraft spacecraft = new Spacecraft();
        spacecraft.setSeatCapacity(10);

        Flight flight = new Flight();
        flight.setId(UUID.fromString("c0000001-0000-0000-0000-000000000001"));
        flight.setStatus(FlightStatus.SCHEDULED);
        flight.setDepartureDate(LocalDateTime.parse("2030-06-15T10:00:00"));
        flight.setSpacecraft(spacecraft);

        when(flightRepository.findByOriginPlanetAndDestinationPlanet(origin, destination))
                .thenReturn(List.of(flight));
        when(spaceFlightBookingRepository.countByFlightId(flight.getId()))
                .thenReturn(0L);
        when(spaceFlightBookingRepository.save(any(SpaceFlightBooking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // act
        SpaceFlightBooking bookingResult = spaceFlightBookingService.bookFlight(
                userId, origin, destination, preferredDate);

        // assert
        assertSame(user, bookingResult.getUser());
        assertSame(flight, bookingResult.getFlight());
        assertEquals(PaymentStatus.SUCCESS, bookingResult.getPaymentStatus());
        assertNotNull(bookingResult.getFinalPrice());
        //assertSame(a, b) — sprawdza, czy to ta sama referencja w pamięci (a == b). Tu chcesz dokładnie tego: na rezerwacji ma być ten sam user i ten sam flight, które przygotowałaś.
        //assertEquals(a, b) — dla obiektów zwykle używa equals(). Jeśli User / Flight nie nadpisują equals, domyślnie zachowuje się jak ==, więc często zadziała podobnie — ale semantycznie mylisz intencję („czy to ten sam obiekt?” vs „czy według equals są równe?”)
        //Dobra praktyka: dla referencji ustawionych w serwisie assertSame jest czytelniejsze.
        verify(spaceFlightBookingRepository).save(any(SpaceFlightBooking.class));
        verify(userRepository).findById(userId);
        verify(flightRepository).findByOriginPlanetAndDestinationPlanet(origin, destination);
        //id rezerwacji — pojawia się dopiero po save z generatorem;
        // w teście save zwraca ten sam obiekt bez ustawiania id w serwisie -> fałszywy fail gdyby assercia not null
    }


}

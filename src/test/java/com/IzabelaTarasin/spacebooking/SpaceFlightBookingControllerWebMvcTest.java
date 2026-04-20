package com.IzabelaTarasin.spacebooking;

import com.IzabelaTarasin.spacebooking.controller.SpaceFlightBookingController;
import com.IzabelaTarasin.spacebooking.dto.SpaceFlightBookingMapper;
import com.IzabelaTarasin.spacebooking.dto.SpaceFlightBookingResponse;
import com.IzabelaTarasin.spacebooking.model.PaymentStatus;
import com.IzabelaTarasin.spacebooking.model.Planet;
import com.IzabelaTarasin.spacebooking.model.SpaceFlightBooking;
import com.IzabelaTarasin.spacebooking.repository.PlanetRepository;
import com.IzabelaTarasin.spacebooking.service.SpaceFlightBookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SpaceFlightBookingController.class)
class SpaceFlightBookingControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SpaceFlightBookingService spaceFlightBookingService;
    @MockitoBean
    private SpaceFlightBookingMapper spaceFlightBookingMapper;
    @MockitoBean
    private PlanetRepository planetRepository;
    @Test
    public void postCreateSpaceFlightBooking_returns201LocationAndJsonBody() throws Exception {
        UUID userId = UUID.fromString("a0000001-0000-0000-0000-000000000099");
        UUID originId = UUID.fromString("b0000001-0000-0000-0000-000000000001");
        UUID destinationId = UUID.fromString("b0000001-0000-0000-0000-000000000002");
        UUID bookingId = UUID.fromString("c0000001-0000-0000-0000-000000000099");
        Planet origin = new Planet();
        origin.setName("Earth");
        Planet destination = new Planet();
        destination.setName("Mars");

        when(planetRepository.findById(originId)).thenReturn(Optional.of(origin));
        when(planetRepository.findById(destinationId)).thenReturn(Optional.of(destination));

        SpaceFlightBooking saved = new SpaceFlightBooking();

        when(spaceFlightBookingService.bookFlight(
                eq(userId), eq(origin), eq(destination), any(LocalDateTime.class)))
                .thenReturn(saved);

        SpaceFlightBookingResponse dto = new SpaceFlightBookingResponse();
        dto.setId(bookingId);
        dto.setUserId(userId);
        dto.setFlightId(UUID.fromString("d0000001-0000-0000-0000-000000000001"));
        dto.setFinalPrice(BigDecimal.valueOf(123));
        dto.setPaymentStatus(PaymentStatus.SUCCESS);

        when(spaceFlightBookingMapper.toDTO(saved)).thenReturn(dto);

        String json = """
                {
                  "originPlanetId": "%s",
                  "destinationPlanetId": "%s",
                  "preferredDate": "2030-01-01T00:00:00"
                }
                """.formatted(originId, destinationId);

        mockMvc.perform(post("/users/{userId}/bookings", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, containsString(bookingId.toString())))
                .andExpect(jsonPath("$.id").value(bookingId.toString()))
                .andExpect(jsonPath("$.finalPrice").value(123));
    }
}
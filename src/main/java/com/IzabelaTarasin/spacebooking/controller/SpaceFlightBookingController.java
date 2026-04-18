package com.IzabelaTarasin.spacebooking.controller;

import com.IzabelaTarasin.spacebooking.dto.CreateSpaceFlightBookingRequest;
import com.IzabelaTarasin.spacebooking.dto.SpaceFlightBookingMapper;
import com.IzabelaTarasin.spacebooking.dto.SpaceFlightBookingResponse;
import com.IzabelaTarasin.spacebooking.error.NotFoundException;
import com.IzabelaTarasin.spacebooking.model.Planet;
import com.IzabelaTarasin.spacebooking.model.SpaceFlightBooking;
import com.IzabelaTarasin.spacebooking.repository.PlanetRepository;
import com.IzabelaTarasin.spacebooking.service.SpaceFlightBookingService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.net.URI;
import java.util.UUID;

@RestController
public class SpaceFlightBookingController {
    private final SpaceFlightBookingService spaceFlightBookingService;
    private final SpaceFlightBookingMapper spaceFlightBookingMapper;
    private final PlanetRepository planetRepository;

    public SpaceFlightBookingController(SpaceFlightBookingService spaceFlightBookingService,
                                        SpaceFlightBookingMapper spaceFlightBookingMapper,
                                        PlanetRepository planetRepository){
        this.spaceFlightBookingService = spaceFlightBookingService;
        this.spaceFlightBookingMapper = spaceFlightBookingMapper;
        this.planetRepository = planetRepository;
    }

    @PostMapping("/users/{userId}/bookings")
    public ResponseEntity<SpaceFlightBookingResponse> createSpaceFlightBooking(@PathVariable UUID userId,
                                                   @RequestBody @Valid CreateSpaceFlightBookingRequest request){
        Planet originPlanet = planetRepository
                .findById(request.getOriginPlanetId())
                .orElseThrow(() -> new NotFoundException(
                "ORIGIN_PLANET_NOT_FOUND",
                "Nie znaleziono planety o id: " + request.getOriginPlanetId()));

        Planet destinationPlanet = planetRepository
                .findById(request.getDestinationPlanetId())
                .orElseThrow(() -> new NotFoundException(
                "DESTINATION_PLANET_NOT_FOUND",
                "Nie znaleziono planety o id: " + request.getDestinationPlanetId()));

        SpaceFlightBooking saveBooking = spaceFlightBookingService
                .bookFlight(userId, originPlanet, destinationPlanet, request.getPreferredDate());

        SpaceFlightBookingResponse bodyBooking = spaceFlightBookingMapper.toDTO(saveBooking);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bodyBooking.getId())
                .toUri();

        return ResponseEntity.created(location).body(bodyBooking);
    }


}

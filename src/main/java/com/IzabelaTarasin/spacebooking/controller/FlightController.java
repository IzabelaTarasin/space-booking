package com.IzabelaTarasin.spacebooking.controller;

import com.IzabelaTarasin.spacebooking.dto.CreateFlightRequest;
import com.IzabelaTarasin.spacebooking.dto.FlightMapper;
import com.IzabelaTarasin.spacebooking.dto.FlightResponse;
import com.IzabelaTarasin.spacebooking.dto.UpdateFlightRequest;
import com.IzabelaTarasin.spacebooking.model.Flight;
import com.IzabelaTarasin.spacebooking.model.FlightStatus;
import com.IzabelaTarasin.spacebooking.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class FlightController {
    private final FlightService flightService;
    private final FlightMapper flightMapper;

    public FlightController(FlightService flightService, FlightMapper flightMapper){
        this.flightService = flightService;
        this.flightMapper = flightMapper;
    }

    @PostMapping("/flights")
    public ResponseEntity<FlightResponse> createFlight(@RequestBody @Valid CreateFlightRequest createFlightRequest){
        Flight flight = flightMapper.toEntity(createFlightRequest);
        Flight flightSaved = flightService.createFlight(flight);
        FlightResponse flightResponse = flightMapper.toDTO(flightSaved);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(flightResponse.getId())
                .toUri();

        //kontroluje pełną odpowiedź HTTP: status, nagłówek i body - 201 CREATED
        return ResponseEntity.created(location).body(flightResponse);
    }
    @PutMapping("/flights/{id}")
    public FlightResponse updateFlight(@PathVariable UUID id, @RequestBody @Valid UpdateFlightRequest updateFlightRequest){
        Flight flight = flightMapper.toEntity(updateFlightRequest);
        Flight flightSaved = flightService.updateFlight(id,flight);
        return flightMapper.toDTO(flightSaved);
    }
    /*
    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable UUID id){
        flightService.deleteFlight(id);
    }
    */
    @DeleteMapping("/flights/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable UUID id){
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/flights")
    public List<FlightResponse> getAllFlights(){
        return flightMapper.toFlightResponseList(flightService.getAllFlights());
    }

    @GetMapping("/flights/{id}")
    public FlightResponse getFlightById(@PathVariable UUID id){
        return flightMapper.toDTO(flightService.getFlightById(id));
    }

    @GetMapping("/flights/number/{flightNumber}")
    public FlightResponse getFlightByNumber(@PathVariable String flightNumber) {
        return flightMapper.toDTO(flightService.getFlightByNumber(flightNumber));
    }

    @GetMapping("/flights/by-status")
    public List<FlightResponse> getFlightsByStatus(@RequestParam FlightStatus status) {
        return flightMapper.toFlightResponseList(flightService.getFlightsByStatus(status));
    }

    @GetMapping("/flights/by-departure-after")
    public List<FlightResponse> getFlightsByDepartureDateAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureAfter){
        return flightMapper.toFlightResponseList(flightService.getFlightsByDepartureDateAfter(departureAfter));
    }

    @GetMapping("/flights/by-arrival-after")
    public List<FlightResponse> getFlightsByArrivalDateAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDate){
        return flightMapper.toFlightResponseList(flightService.getFlightsByArrivalDateAfter(arrivalDate));
    }

    @GetMapping("/flights/cheap")
    public List<FlightResponse> getCheapFlights(@RequestParam BigDecimal maxPrice){
        return flightMapper.toFlightResponseList(flightService.getCheapFlights(maxPrice));
    }
}

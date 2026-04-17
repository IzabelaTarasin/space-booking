package com.IzabelaTarasin.spacebooking.controller;

import com.IzabelaTarasin.spacebooking.dto.*;
import com.IzabelaTarasin.spacebooking.model.User;
import com.IzabelaTarasin.spacebooking.service.FlightService;
import com.IzabelaTarasin.spacebooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import java.util.List;
import java.util.UUID;

@RestController

public class UserController {
    private final UserService userService; //raz przypisana wartość nie może zostać zmieniona przez cały czas życia obiektu
    private final UserMapper userMapper;
    private final FlightMapper flightMapper;
    private final FlightService flightService;
    /*
    final -> Bezpieczeństwo wątkowe: W aplikacjach webowych wielu użytkowników korzysta z jednego kontrolera naraz. Pola final są bezpieczniejsze w takim środowisku, bo nikt ich nie zmodyfikuje w trakcie działania.
    */

    public UserController (UserService userService, UserMapper userMapper, FlightService flightService, FlightMapper flightMapper){
        this.userService = userService;
        this.userMapper = userMapper;
        this.flightService = flightService;
        this.flightMapper = flightMapper;
    }

    //dodanie użytkownika
    /*@PostMapping("/users")
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest createUserRequest){
        User user = userMapper.toEntity(createUserRequest);
        User saved = userService.createUser(user); //zapis do bazy
        return userMapper.toDTO(saved); //odpowiedź dla klienta w formie DTO po udanym zapisie
    }
    ponizej zwracam 201 created dla POST - lepsza praktyka
    */

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        // 1) JSON z requestu -> encja User
        User user = userMapper.toEntity(createUserRequest);
        // 2) zapis do bazy (po zapisie user ma np. wygenerowane id)
        User saved = userService.createUser(user);
        // 3) encja -> DTO do odpowiedzi (bez password)
        UserResponse response = userMapper.toDTO(saved);
        // 4) składamy URL do nowo utworzonego usera: /users/{id} jakomlokalizacja do nagłowka
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        // 5) zwracam 201 Created + Location nagłówek + body
        return ResponseEntity.created(location).body(response);
        //ResponseEntity pozwala kontrolować pełną odpowiedź HTTP: status, nagłówki i body.
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers(){
        return userMapper.toUserResponseList(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public UserResponse getUserById(@PathVariable UUID id){
        return userMapper.toDTO(userService.getUserById(id));
    }

    @GetMapping("/users/{userId}/flights")
    public List<FlightResponse> getAllFlightByUserId(@PathVariable UUID userId){
        return flightMapper.toFlightResponseList(flightService.getAllFlightByUserId(userId));
    }
}

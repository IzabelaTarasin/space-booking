package com.IzabelaTarasin.spacebooking.controller;

import com.IzabelaTarasin.spacebooking.dto.LoginRequest;
import com.IzabelaTarasin.spacebooking.dto.UserMapper;
import com.IzabelaTarasin.spacebooking.dto.UserResponse;
import com.IzabelaTarasin.spacebooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/auth/login")
    public UserResponse login(@RequestBody @Valid LoginRequest request){
        return userMapper.toDTO(userService.login(request.getEmail(), request.getPassword()));
    }
}

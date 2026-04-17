package com.IzabelaTarasin.spacebooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class UserResponse {
    private UUID id;
    private String email;
    private String telephoneNumber;
    private String firstName;
    private String lastName;
}

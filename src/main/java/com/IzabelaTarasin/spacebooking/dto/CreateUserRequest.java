package com.IzabelaTarasin.spacebooking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateUserRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 5, max = 32)
    private String telephoneNumber;
    @NotBlank
    @Size(min = 6, max = 128)
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}

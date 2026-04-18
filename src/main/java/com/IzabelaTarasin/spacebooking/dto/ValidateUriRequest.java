package com.IzabelaTarasin.spacebooking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ValidateUriRequest {
    @NotBlank(message = "Pole uri jest wymagane")
    private String uri;
}

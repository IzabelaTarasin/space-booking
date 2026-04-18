package com.IzabelaTarasin.spacebooking.controller;

import com.IzabelaTarasin.spacebooking.dto.ValidateUriRequest;
import com.IzabelaTarasin.spacebooking.dto.ValidateUriResponse;
import com.IzabelaTarasin.spacebooking.service.UriValidationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
public class UriValidationController {
    private final UriValidationService uriValidationService;
    public UriValidationController(UriValidationService uriValidationService) {
        this.uriValidationService = uriValidationService;
    }
    @PostMapping("/validate-uri")
    public ValidateUriResponse validateUri(@RequestBody @Valid ValidateUriRequest request) {
        URI uri = uriValidationService.parseAndValidate(request.getUri());
        return new ValidateUriResponse(uri.toASCIIString());
    }
}

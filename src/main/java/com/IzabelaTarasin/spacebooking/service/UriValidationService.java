package com.IzabelaTarasin.spacebooking.service;

import com.IzabelaTarasin.spacebooking.error.BadRequestException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
@Service
public class UriValidationService {
    public URI parseAndValidate(String raw) {
        try {
            return new URI(raw.trim());
        } catch (URISyntaxException e) {
            throw new BadRequestException(
                    "INVALID_URI",
                    "Niepoprawny skład URI: " + e.getReason()
            );
        }
    }
}

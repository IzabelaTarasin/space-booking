package com.IzabelaTarasin.spacebooking.service;

import com.IzabelaTarasin.spacebooking.error.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
@Service
public class UriValidationService {
    private static final Logger log = LoggerFactory.getLogger(UriValidationService.class);

    public URI parseAndValidate(String raw) {
        try {
            URI uri = new URI(raw.trim());
            log.info("URL poprawny składniowo: {}", uri);
            return uri;
        } catch (URISyntaxException ex) {
            throw new BadRequestException(
                    "INVALID_URI",
                    "Niepoprawny skład URI: " + ex.getReason()
            );
        }
    }
}

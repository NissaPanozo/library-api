package com.library.library_api.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class GoogleBooksService {

    private final RestTemplate restTemplate = new RestTemplate();

    @CircuitBreaker(name = "googleBooks", fallbackMethod = "fallback")
    public Map searchBooks(String title) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + title;
        log.info("Anropar Google Books API för: {}", title);
        return restTemplate.getForObject(url, Map.class);
    }

    public Map fallback(String title, Throwable ex) {
        log.warn("Circuit Breaker aktiverad! Orsak: {}", ex.getMessage());
        return Map.of(
                "error", "Google Books API är inte tillgängligt just nu",
                "sokord", title
        );
    }
}
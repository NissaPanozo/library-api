package com.library.library_api.controller;

import com.library.library_api.service.GoogleBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/google-books")
@RequiredArgsConstructor

public class GoogleBooksController {
    private final GoogleBooksService googleBooksService;

    @GetMapping("/search")
    public ResponseEntity<Map> searchBooks(@RequestParam String title) {
        Map result = googleBooksService.searchBooks(title);
        return ResponseEntity.ok(result);
    }
}
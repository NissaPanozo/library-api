package com.library.library_api.controller;

import com.library.library_api.dto.AuthorRequest;
import com.library.library_api.dto.AuthorResponse;
import com.library.library_api.dto.BookResponse;
import com.library.library_api.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;


    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse response = authorService.createAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getBooksByAuthor(id));
    }
}


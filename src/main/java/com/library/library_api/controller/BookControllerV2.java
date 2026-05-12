package com.library.library_api.controller;

import com.library.library_api.dto.BookResponseV2;
import com.library.library_api.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/books")
@RequiredArgsConstructor
public class BookControllerV2 {

    private final BookService bookService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBooksV2() {
        List<BookResponseV2> books = bookService.getAllBooksV2();

        Map<String, Object> response = new HashMap<>();
        response.put("data", books);
        response.put("version", "v2");

        return ResponseEntity.ok(response);
    }
}
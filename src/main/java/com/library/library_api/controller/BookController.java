package com.library.library_api.controller;
import com.library.library_api.dto.BookRequest;
import com.library.library_api.dto.BookResponse;
import com.library.library_api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook (@Valid @RequestBody BookRequest request){
      BookResponse response = bookService.createBook(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<BookResponse>> getAllBooksPaged(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooksPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookId(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}

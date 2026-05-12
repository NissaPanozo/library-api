package com.library.library_api.service;

import com.library.library_api.dto.BookRequest;
import com.library.library_api.dto.BookResponse;
import com.library.library_api.dto.BookResponseV2;
import com.library.library_api.entity.Book;
import com.library.library_api.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookResponse createBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublishedYear(request.getPublishedYear());

        Book saved = bookRepository.save(book);
        return toResponse(saved);

    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<BookResponse> getAllBooksPaged(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Cacheable(value = "books", key = "#id")
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bok med id " + id + " hittades inte"));
        return toResponse(book);
    }

    private BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getIsbn());
        response.setPublishedYear(book.getPublishedYear());
        return response;
    }

    public List<BookResponseV2> getAllBooksV2() {
        return bookRepository.findAll()
                .stream()
                .map(this::toResponseV2)
                .collect(Collectors.toList());
    }

    private BookResponseV2 toResponseV2(Book book) {
        BookResponseV2 response = new BookResponseV2();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getIsbn());
        response.setPublishedYear(book.getPublishedYear());
        response.setAvailable(true);
        return response;
    }
}

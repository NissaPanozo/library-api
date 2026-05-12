package com.library.library_api.service;

import com.library.library_api.dto.AuthorRequest;
import com.library.library_api.dto.AuthorResponse;
import com.library.library_api.dto.BookResponse;
import com.library.library_api.entity.Author;
import com.library.library_api.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorResponse createAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setName(request.getName());

        Author saved = authorRepository.save(author);
        return toResponse(saved);
    }

    public AuthorResponse getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Författare med id " + id + " hittades inte"));
        return toResponse(author);

}
    public List<BookResponse> getBooksByAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Författare med id " + id + " hittades inte"));

        return author.getBooks()
                .stream()
                .map(book -> {
                    BookResponse response = new BookResponse();
                    response.setId(book.getId());
                    response.setTitle(book.getTitle());
                    response.setAuthor(book.getAuthor());
                    response.setIsbn(book.getIsbn());
                    response.setPublishedYear(book.getPublishedYear());
                    return response;
                })
                .collect(Collectors.toList());
    }

    private AuthorResponse toResponse(Author author) {
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(author.getName());
        return response;
    }
}

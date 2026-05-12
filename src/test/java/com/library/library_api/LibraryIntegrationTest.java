package com.library.library_api;

import com.library.library_api.dto.AuthorRequest;
import com.library.library_api.dto.BookRequest;
import com.library.library_api.dto.LoanRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LibraryIntegrationTest {

    // Använder admin/admin för alla requests
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private com.library.library_api.repository.BookRepository bookRepository;

    @Autowired
    private com.library.library_api.repository.LoanRepository loanRepository;

    @Autowired
    private com.library.library_api.repository.AuthorRepository authorRepository;

    private TestRestTemplate authTemplate;

    @BeforeEach
    void setUp() {
        loanRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        // Skapar en autentiserad klient med admin/admin
        authTemplate = restTemplate.withBasicAuth("admin", "admin");
    }

    @Test
    void skapaAuthor_returnerar201() {
        AuthorRequest request = new AuthorRequest();
        request.setName("Robert Martin");

        ResponseEntity<Object> response = authTemplate.postForEntity(
                "/api/v1/authors", request, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void skapaBook_returnerar201() {
        BookRequest request = new BookRequest();
        request.setTitle("Clean Code");
        request.setAuthor("Robert Martin");
        request.setPublishedYear(2008);

        ResponseEntity<Object> response = authTemplate.postForEntity(
                "/api/v1/books", request, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void skapaLoan_returnerar201() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Clean Code");
        bookRequest.setAuthor("Robert Martin");
        bookRequest.setPublishedYear(2008);
        ResponseEntity<java.util.Map> bookResponse = authTemplate.postForEntity(
                "/api/v1/books", bookRequest, java.util.Map.class);
        Long bookId = Long.valueOf(bookResponse.getBody().get("id").toString());

        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBookId(bookId);
        ResponseEntity<Object> loanResponse = authTemplate.postForEntity(
                "/api/v1/loans", loanRequest, Object.class);

        assertThat(loanResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void skapaLoan_bokRedanUtlanad_returnerar400() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Clean Code");
        bookRequest.setAuthor("Robert Martin");
        bookRequest.setPublishedYear(2008);
        ResponseEntity<java.util.Map> bookResponse = authTemplate.postForEntity(
                "/api/v1/books", bookRequest, java.util.Map.class);
        Long bookId = Long.valueOf(bookResponse.getBody().get("id").toString());

        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBookId(bookId);
        authTemplate.postForEntity("/api/v1/loans", loanRequest, Object.class);

        ResponseEntity<Object> response = authTemplate.postForEntity(
                "/api/v1/loans", loanRequest, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void hamtaBook_finnsEj_returnerar404() {
        ResponseEntity<Object> response = authTemplate.getForEntity(
                "/api/v1/books/999", Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
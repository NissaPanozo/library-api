package com.library.library_api;

import com.library.library_api.dto.BookRequest;
import com.library.library_api.dto.LoanRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConcurrencyTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private com.library.library_api.repository.LoanRepository loanRepository;

    @Autowired
    private com.library.library_api.repository.BookRepository bookRepository;

    @Autowired
    private com.library.library_api.repository.AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        loanRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void fleraAnvandare_lanarSamtidigt_endastEttLanSkapas() throws InterruptedException {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Clean Code");
        bookRequest.setAuthor("Robert Martin");
        java.util.Map bookResponse = restTemplate.postForEntity(
                "/api/v1/books", bookRequest, java.util.Map.class).getBody();
        Long bookId = Long.valueOf(bookResponse.get("id").toString());

        int antalTrådar = 100;
        ExecutorService executor = Executors.newFixedThreadPool(antalTrådar);
        AtomicInteger lyckades = new AtomicInteger(0);  // räknar lyckade lån
        AtomicInteger misslyckades = new AtomicInteger(0); // räknar misslyckade lån

        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBookId(bookId);

        for (int i = 0; i < antalTrådar; i++) {
            executor.submit(() -> {
                var response = restTemplate.postForEntity(
                        "/api/v1/loans", loanRequest, Object.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    lyckades.incrementAndGet();
                } else {
                    misslyckades.incrementAndGet();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);


        assertThat(lyckades.get()).isEqualTo(1);
        assertThat(misslyckades.get()).isEqualTo(99);
        assertThat(loanRepository.count()).isEqualTo(1);
    }
}
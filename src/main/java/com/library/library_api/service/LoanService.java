package com.library.library_api.service;

import com.library.library_api.dto.LoanRequest;
import com.library.library_api.dto.LoanResponse;
import com.library.library_api.entity.Book;
import com.library.library_api.entity.Loan;
import com.library.library_api.exception.BookAlreadyLoanedException;
import com.library.library_api.repository.BookRepository;
import com.library.library_api.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;


    @Transactional
    public LoanResponse createLoan(LoanRequest request) {

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Bok med id " + request.getBookId() + " hittades inte"));

        loanRepository.findByBookAndReturnDateIsNull(book).ifPresent(loan -> {
            throw new BookAlreadyLoanedException("Boken är redan utlånad");        });

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(null);

        Loan saved = loanRepository.save(loan);
        return toResponse(saved);
    }

    public List<LoanResponse> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private LoanResponse toResponse(Loan loan) {
        LoanResponse response = new LoanResponse();
        response.setId(loan.getId());
        response.setBookId(loan.getBook().getId());
        response.setBookTitle(loan.getBook().getTitle());
        response.setLoanDate(loan.getLoanDate());
        response.setReturnDate(loan.getReturnDate());
        return response;
    }
}


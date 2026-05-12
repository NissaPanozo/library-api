package com.library.library_api.repository;

import com.library.library_api.entity.Loan;
import com.library.library_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    
    Optional<Loan> findByBookAndReturnDateIsNull(Book book);
}

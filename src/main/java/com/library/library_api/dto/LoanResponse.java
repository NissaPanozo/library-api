package com.library.library_api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LoanResponse {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private LocalDate loanDate;
    private LocalDate returnDate;
}
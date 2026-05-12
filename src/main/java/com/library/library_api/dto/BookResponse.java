package com.library.library_api.dto;

import lombok.Data;

@Data
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
}

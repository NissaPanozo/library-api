package com.library.library_api.dto;

import lombok.Data;

@Data
public class BookResponseV2 {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private boolean available;
}

package com.library.library_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class AuthorResponse {
    private Long id;
    private String name;
    private List<BookResponse> books;
}

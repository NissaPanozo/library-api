package com.library.library_api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank(message = "Titel får inte vara tom")
    @Size(min = 1, max = 100, message = "Titel måste vara mellan 1 och 100 tecken")
    private String title;

    @NotBlank(message = "Författare får inte vara tom")
    @Size(min = 1, max = 100, message = "Författarnamn måste vara mellan 1 och 100 tecken")
    private String author;

    @Pattern(regexp = "^[0-9-]*$", message = "ISBN får endast innehålla siffror och bindestreck")
    private String isbn;

    @Min(value = 1000, message = "Publiceringsår måste vara minst 1000")
    @Max(value = 2100, message = "Publiceringsår kan inte vara i framtiden")
    private int publishedYear;
}
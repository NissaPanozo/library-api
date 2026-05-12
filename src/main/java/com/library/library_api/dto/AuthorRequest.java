package com.library.library_api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AuthorRequest {

    @NotBlank(message = "Namn får inte vara tomt")
    @Size(min = 1, max = 100, message = "Namn måste vara mellan 1 och 100 tecken")
    private String name;
}
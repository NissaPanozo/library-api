package com.library.library_api.entity;

import jakarta.persistence.Version;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private String isbn;

    private int publishedYear;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author authorEntity;

    @Version
    private Long version;

}
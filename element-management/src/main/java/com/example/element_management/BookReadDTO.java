package com.example.element_management;

import java.util.UUID;

public class BookReadDTO {
    private UUID id;
    private String title;
    private String genre;

    public BookReadDTO() {}
    public BookReadDTO(UUID id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }


    public UUID getId() {
        return id;
    }


    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

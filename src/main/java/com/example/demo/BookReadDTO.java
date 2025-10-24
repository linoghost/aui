package com.example.demo;

import java.util.UUID;

public class BookReadDTO {
    private UUID id;
    private String title;
    private String genre;
    private String authorName; // "Stephen King"

    public BookReadDTO() {}
    public BookReadDTO(UUID id, String title, String genre, String authorName) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthorName() {
        return authorName;
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

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

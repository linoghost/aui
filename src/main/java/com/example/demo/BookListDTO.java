package com.example.demo;

import java.util.UUID;

public class BookListDTO {
    private UUID id;
    private String title;
    private String authorName;

    public BookListDTO() {}
    public BookListDTO(UUID id, String title, String authorName) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
    }
    public String getAuthorName() {
        return authorName;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

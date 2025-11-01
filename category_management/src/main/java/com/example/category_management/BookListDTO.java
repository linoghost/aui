package com.example.category_management;

import java.util.UUID;

public class BookListDTO {
    private UUID id;
    private String title;

    public BookListDTO() {}
    public BookListDTO(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

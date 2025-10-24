package com.example.demo;

public class BookCreateUpdateDTO {
    private String title;
    private String genre;

    public BookCreateUpdateDTO() {}
    public BookCreateUpdateDTO(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }
}

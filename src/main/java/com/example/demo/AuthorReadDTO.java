package com.example.demo;

import java.util.*;

public class AuthorReadDTO {

    private UUID id;
    private String name;
    private String surname;

    private  List<String> booktitles;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooktitles(List<String> booktitles) {
        this.booktitles = booktitles;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public List<String> getBooktitles() {
        return booktitles;
    }

    public UUID getId() {
        return id;
    }
}

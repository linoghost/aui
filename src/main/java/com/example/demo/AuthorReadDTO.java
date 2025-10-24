package com.example.demo;

import java.util.*;

public class AuthorReadDTO { //chcemy pełnego info, co napisał etc (mam tu troche
    //za mało danych żeby to odpowiednio przedstawić ale tu by było wszystko jak wiek,
    //nwm zarobki, a w list byłoby tylko jego nazwisko np)

    private UUID id;
    private String name;
    private String surname;

    private  List<String> booktitles;
    public AuthorReadDTO(UUID id, String name, String surname, List<String> books) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.books = books;
    }

    public AuthorReadDTO(){}

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

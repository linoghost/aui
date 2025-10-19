package com.example.demo;

public class AuthorCreateUpdateDTO {
    private String name;
    private String surname;
    //id bedzie generowane


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}

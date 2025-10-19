package com.example.demo;

import java.util.UUID;

public class AuthorListDTO {
    private UUID id;
    private String fullName;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
}

package com.example.element_management;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="books")
public class Book implements Comparable<Book>, Serializable {
//element kategorii
    @Id
    private UUID id;
    @Column(name="title")
    private String title;
    @Column(name = "genre")
    private String genre;

    //relacja N:1 - wiele ksiazek do 1 autora
    // @ManyToOne(fetch = FetchType.LAZY)
    @Column(name="author_id")
    private UUID author_id;

    public Book(){}

    public Book(UUID id, String title, String genre, UUID author_id) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author_id = author_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id == book.id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }



    @Override
    public String toString() {
        return "Book{id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'';
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public UUID getAuthorID() {
        return author_id;
    }

    public void setAuthorID(UUID author_id) {
        this.author_id=author_id;
    }

//    public BookDTO toDTO() {
//        String authorName;
//        if (author != null) {
//            authorName = author.getName() + " " + author.getSurname();
//        } else {
//            authorName = "unknown";
//        }
//
//        return new BookDTO(id, title, genre, authorName);
//    }

}

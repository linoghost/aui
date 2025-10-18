package com.example.demo;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="authors")
public class Author implements Comparable<Author>, Serializable {

    @Id
    private UUID id;
    @Column(name="name")
    private String name;
    @Column(name="surname")
    private String surname;

    //relacja 1:n bo autor ma wiele książek
    @OneToMany(mappedBy="author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    //fetchtype lazy żeby nam nie fetchowalo od razu po załadowaniu wszystkich ksiazek,
    //tak jak było w instrukcji
    private List<Book> books;

    public Author(){}

    public Author(UUID id, String name, String surname, List<Book> books){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.books = books;
    }
    //konstruktor just in case gdyby miał się przydać do testów :)

    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o; //musimy rzutować o na autora bo w argumencie jest jako typ Object
        //a do porównania muszą być tego samego typu
        return id == author.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); //trzeba nadpisać hash żeby faktycznie rozróżniało obiekty bo domyślnie rozróżnia po adresie
    }

    @Override
    public int compareTo(Author other) { //nadpis zeby porównywało po nazwiskach
        return this.surname.compareToIgnoreCase(other.surname);
    }

    @Override
    public String toString(){
        if (books == null || books.isEmpty()) {
            return "Author{id=" + id +
                    ", name='" + name + " " + surname + '\'' +
                    ", books=[]}";
        }

        String titles = "";
        for (Book b : books) {
            titles += b.getTitle() + ", ";
        }

//        if (titles.endsWith(", ")) {
//            titles = titles.substring(0, titles.length() - 2);
//        }

        return "Author{id=" + id +
                ", Name: '" + name + " " + surname + '\'' +
                ", Books: [" + titles + "]}";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
        if (book.getAuthor() != this) {
            book.setAuthor(this); //zabezpieczenie
        }
    }







}
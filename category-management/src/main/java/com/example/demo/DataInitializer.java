package com.example.demo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import java.util.*;


@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    private final AuthorService authorService;


    public DataInitializer(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("yippiee!");
        List<Author> authors = createAuthors();

        for (Author author : authors) {
            authorService.save(author);
            System.out.println("Saved author: " + author.getName());
        }

        System.out.println("yippiee!");
    }

    // funkcja stricte żeby nie zaśmiecać run, tak jak poprzednio w mainie robilam
    private static List<Author> createAuthors() {
        Author king = new Author(UUID.randomUUID(), "Stephen", "King");
        Author tolkien = new Author(UUID.randomUUID(), "J.R.R.", "Tolkien");
        Author rowling = new Author(UUID.randomUUID(), "J.K.", "Rowling");
        Author orwell = new Author(UUID.randomUUID(), "George", "Orwell");
        Author austen = new Author(UUID.randomUUID(), "Jane", "Austen");
        Author herbert = new Author(UUID.randomUUID(), "Frank", "Herbert");

        List<Author> authors = new ArrayList<>();
        authors.add(king);
        authors.add(tolkien);
        authors.add(rowling);
        authors.add(orwell);
        authors.add(austen);
        authors.add(herbert);

        return authors;
    }

}

package com.example.demo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.*;


@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthorService authorService;
    private final BookService bookService;


    public DataInitializer(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("yippiee!");
        List<Author> authors = createAuthorsAndBooks();

        for (Author author : authors) {
            authorService.save(author);
            System.out.println("Saved author: " + author.getName() + " with " +
                    (author.getBooks() != null ? author.getBooks().size() : 0) + " books");
        }

        List<Book> allBooks = bookService.findAll();
        System.out.println("Total books in database: " + allBooks.size());


        System.out.println("yippiee!");
    }

    // funkcja stricte żeby nie zaśmiecać run, tak jak poprzednio w mainie robilam
    private static List<Author> createAuthorsAndBooks() {
        Author king = new Author(UUID.randomUUID(), "Stephen", "King", new ArrayList<>());
        Author tolkien = new Author(UUID.randomUUID(), "J.R.R.", "Tolkien", new ArrayList<>());
        Author rowling = new Author(UUID.randomUUID(), "J.K.", "Rowling", new ArrayList<>());
        Author orwell = new Author(UUID.randomUUID(), "George", "Orwell", new ArrayList<>());
        Author austen = new Author(UUID.randomUUID(), "Jane", "Austen", new ArrayList<>());
        Author herbert = new Author(UUID.randomUUID(), "Frank", "Herbert", new ArrayList<>());


        Book b1 = new Book(UUID.randomUUID(), "It", "Horror", king);
        Book b2 = new Book(UUID.randomUUID(), "The Shining", "Horror", king);
        king.addBook(b1);
        king.addBook(b2);


        Book b5 = new Book(UUID.randomUUID(), "The Hobbit", "Fantasy", tolkien);
        Book b6 = new Book(UUID.randomUUID(), "The Lord of the Rings", "Fantasy", tolkien);
        tolkien.addBook(b5);
        tolkien.addBook(b6);


        Book b7 = new Book(UUID.randomUUID(), "Harry Potter and the Philosopher's Stone", "Fantasy", rowling);
        Book b8 = new Book(UUID.randomUUID(), "Harry Potter and the Chamber of Secrets", "Fantasy", rowling);
        Book b9 = new Book(UUID.randomUUID(), "Harry Potter and the Prisoner of Azkaban", "Fantasy", rowling);
        rowling.addBook(b7);
        rowling.addBook(b8);
        rowling.addBook(b9);


        Book b10 = new Book(UUID.randomUUID(), "1984", "Dystopian", orwell);
        Book b11 = new Book(UUID.randomUUID(), "Animal Farm", "Political Satire", orwell);
        Book b12 = new Book(UUID.randomUUID(), "Homage to Catalonia", "Non-fiction", orwell);
        orwell.addBook(b10);
        orwell.addBook(b11);
        orwell.addBook(b12);


        Book b13 = new Book(UUID.randomUUID(), "Pride and Prejudice", "Romance", austen);
        Book b14 = new Book(UUID.randomUUID(), "Sense and Sensibility", "Romance", austen);
        Book b15 = new Book(UUID.randomUUID(), "Emma", "Romance", austen);
        austen.addBook(b13);
        austen.addBook(b14);
        austen.addBook(b15);


        Book b16 = new Book(UUID.randomUUID(), "Dune", "Science Fiction", herbert);
        Book b17 = new Book(UUID.randomUUID(), "Dune Messiah", "Science Fiction", herbert);
        Book b18 = new Book(UUID.randomUUID(), "Children of Dune", "Science Fiction", herbert);
        herbert.addBook(b16);
        herbert.addBook(b17);
        herbert.addBook(b18);


        Book b3 = new Book(UUID.randomUUID(), "Carrie", "Horror", king);
        Book b4 = new Book(UUID.randomUUID(), "The Stand", "Horror", king);
        king.addBook(b3);
        king.addBook(b4);


        Book b19 = new Book(UUID.randomUUID(), "The Silmarillion", "Fantasy", tolkien);
        Book b20 = new Book(UUID.randomUUID(), "The Children of Húrin", "Fantasy", tolkien);
        tolkien.addBook(b19);
        tolkien.addBook(b20);



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

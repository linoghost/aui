package com.example.demo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final AuthorService authorService;
    private final BookService bookService;

    public ConsoleRunner(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        List<Author> authors;
        List<Book> books;
        Author author;
        int index;
        while (running) {
            System.out.println("wpisz komende");
            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "help":
                    System.out.println("Available commands: list_authors, list_books, add_book, delete_book, exit");
                    break;
                case "list_authors":
                    authors = authorService.findAll();
                    authors.forEach(authorr -> System.out.println(
                            authorr.getId() + " - " + authorr.getName() + " " + authorr.getSurname()
                    ));

                    break;
                case "list_books":
                    books = bookService.findAll();
                    if (books.isEmpty()) {
                        System.out.println("No books found");
                    } else {
                        System.out.println("List of books:");
                        books.forEach(book -> System.out.println(book.getTitle()));
                    }
                    break;
                case "add_book":
                    System.out.println("Enter book title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter genre:");
                    String genre = scanner.nextLine();


                    authors = authorService.findAll();
                    for (int i = 0; i < authors.size(); i++) {
                        Author a = authors.get(i);
                        System.out.println(i + ": " + a.getName() + " " + a.getSurname());
                    }

                    System.out.println("Select author by index:");
                    index = Integer.parseInt(scanner.nextLine());
                    author = authors.get(index);


                    Book newBook = new Book(UUID.randomUUID(), title, genre, author);
                    authorService.addBookToAuthor(author.getId(), newBook);

                    System.out.println("Book added!");
                    break;

                case "delete_book":
                    System.out.println("Enter book title to delete:");
                    String titlee = scanner.nextLine();
                    Book book = bookService.findByTitle(titlee);
                    bookService.deleteById(book.getId());
                    System.out.println("Book deleted!");
                    break;

                case "delete_author":
                    authors = authorService.findAll();
                    for (int i = 0; i < authors.size(); i++) {
                        Author a = authors.get(i);
                        System.out.println(i + ": " + a.getName() + " " + a.getSurname());
                    }

                    System.out.println("Select author by index:");
                    index = Integer.parseInt(scanner.nextLine());
                    author = authors.get(index);
                    authorService.deleteById(author.getId());
                    System.out.println("Author deleted!");
                    break;


                case "books_by_author":
                    authors = authorService.findAll();
                    for (int i = 0; i < authors.size(); i++) {
                        Author a = authors.get(i);
                        System.out.println(i + ": " + a.getName() + " " + a.getSurname());
                    }

                    System.out.println("Select author by index:");
                    index = Integer.parseInt(scanner.nextLine());
                    author = authors.get(index);

                    books = bookService.findByAuthor(author);
                    books.forEach(boook -> System.out.println(boook.getTitle()));
                    break;


                case "exit":
                    running = false;
                    break;
                default:
                    System.out.println("no cos nie tak");
            }
        }

        System.out.println("Application stopped.");
    }
}

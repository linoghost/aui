package com.example.element_management;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.annotation.Order;
import java.util.*;


@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    private final BookService bookService;
    private final WebClient webClient;


    public DataInitializer(BookService bookService, WebClient.Builder webClientBuilder) {
        this.bookService = bookService;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/authors").build();
    }

    @Override
    public void run(String... args) throws Exception {
        List<AuthorListDTO> authors = webClient.get()
                .uri("") // GET http://localhost:8081/authors
                .retrieve()
                .bodyToFlux(AuthorListDTO.class)
                .collectList()
                .block();

        if (authors == null || authors.isEmpty()) {
            System.out.println("mamy problem, nie ma autorów");
            return;
        }

        Map<String, UUID> authorMap = new HashMap<>();
        //robimy associating ID z imieniem i nazwiskiem - hash bo jest najszybszy
        for (AuthorListDTO a : authors) {
            authorMap.put(a.getFullName(), a.getId());
        }

        List<Book> books = new ArrayList<>();

        books = createBooks(authorMap);
        for (Book book : books) {
            if (book.getAuthorID() == null) {
                System.out.println("cos poszlo nie tak z dodawaniem id autora" + book.getTitle());
                continue;
            }

            bookService.save(book);
            System.out.println("zapisano " + book.getTitle());
        }

        System.out.println("yippiee! ksiazki!");
    }

    // funkcja stricte żeby nie zaśmiecać run
    private static List<Book> createBooks(Map<String, UUID> authorMap) {

        List<Book> books = new ArrayList<>();

        Book b1 = new Book(UUID.randomUUID(), "It", "Horror", authorMap.get("Stephen King"));
        books.add(b1);
        Book b2 = new Book(UUID.randomUUID(), "The Shining", "Horror", authorMap.get("Stephen King"));
        books.add(b2);

        Book b5 = new Book(UUID.randomUUID(), "The Hobbit", "Fantasy", authorMap.get("J.R.R. Tolkien"));
        books.add(b5);
        Book b6 = new Book(UUID.randomUUID(), "The Lord of the Rings", "Fantasy", authorMap.get("J.R.R. Tolkien"));
        books.add(b6);

        Book b7 = new Book(UUID.randomUUID(), "Harry Potter and the Philosopher's Stone", "Fantasy", authorMap.get("J.K. Rowling"));
        books.add(b7);
        Book b8 = new Book(UUID.randomUUID(), "Harry Potter and the Chamber of Secrets", "Fantasy", authorMap.get("J.K. Rowling"));
        books.add(b8);
        Book b9 = new Book(UUID.randomUUID(), "Harry Potter and the Prisoner of Azkaban", "Fantasy", authorMap.get("J.K. Rowling"));
        books.add(b9);

        Book b10 = new Book(UUID.randomUUID(), "1984", "Dystopian", authorMap.get("George Orwell"));
        books.add(b10);
        Book b11 = new Book(UUID.randomUUID(), "Animal Farm", "Political Satire", authorMap.get("George Orwell"));
        books.add(b11);
        Book b12 = new Book(UUID.randomUUID(), "Homage to Catalonia", "Non-fiction", authorMap.get("George Orwell"));
        books.add(b12);

        Book b13 = new Book(UUID.randomUUID(), "Pride and Prejudice", "Romance", authorMap.get("Jane Austen"));
        books.add(b13);
        Book b14 = new Book(UUID.randomUUID(), "Sense and Sensibility", "Romance", authorMap.get("Jane Austen"));
        books.add(b14);
        Book b15 = new Book(UUID.randomUUID(), "Emma", "Romance", authorMap.get("Jane Austen"));
        books.add(b15);

        Book b16 = new Book(UUID.randomUUID(), "Dune", "Science Fiction", authorMap.get("Frank Herbert"));
        books.add(b16);
        Book b17 = new Book(UUID.randomUUID(), "Dune Messiah", "Science Fiction", authorMap.get("Frank Herbert"));
        books.add(b17);
        Book b18 = new Book(UUID.randomUUID(), "Children of Dune", "Science Fiction", authorMap.get("Frank Herbert"));
        books.add(b18);

        Book b3 = new Book(UUID.randomUUID(), "Carrie", "Horror", authorMap.get("Stephen King"));
        books.add(b3);
        Book b4 = new Book(UUID.randomUUID(), "The Stand", "Horror", authorMap.get("Stephen King"));
        books.add(b4);

        Book b19 = new Book(UUID.randomUUID(), "The Silmarillion", "Fantasy", authorMap.get("J.R.R. Tolkien"));
        books.add(b19);
        Book b20 = new Book(UUID.randomUUID(), "The Children of Húrin", "Fantasy", authorMap.get("J.R.R. Tolkien"));
        books.add(b20);

        return books;
    }

}

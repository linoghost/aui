package com.example.demo;

import com.example.demo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookController {

    private final AuthorService authorService;
    private final BookService bookService;

    public BookController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @GetMapping("/books")
    public List<BookListDTO> getAllBooks() {
        return bookService.findAll().stream()
                .map(b -> new BookListDTO(b.getId(), b.getTitle(), b.getAuthor().getName() + " " + b.getAuthor().getSurname()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable UUID authorId) {
        //pathvariable bierze wartość z naszego id i zamienia ją na uuid
        //responseentity jest stricte na restapi zeby bylo nam łatwo zwracac odpowiedzi (np 200 OK i reszta danych,
        //albo błędy. bez tego CHYBA może się wywalić jak coś pójdzie nie tak
        Author author = authorService.findById(authorId);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }

        List<Book> books = author.getBooks();
        if (books.isEmpty()) {
            return ResponseEntity.noContent().build(); // różnica: autor istnieje, ale bez książek
        }

        List<BookListDTO> bookDTOs = books.stream()
                .map(b -> new BookListDTO(b.getId(), b.getTitle(), author.getName() + " " + author.getSurname()))
                .toList();

        return ResponseEntity.ok(bookDTOs);


    }
    @PostMapping("/authors/{authorId}/books")
    public ResponseEntity<?> addBook(@PathVariable UUID authorId, @RequestBody BookCreateUpdateDTO dto) {
        Author author = authorService.findById(authorId);
        if (author == null) {
            return ResponseEntity.badRequest().body("Author not found");
        }

        Book newBook = new Book(UUID.randomUUID(), dto.getTitle(), dto.getGenre(), author);
        author.addBook(newBook);
        bookService.save(newBook);

        return ResponseEntity.status(201).body(
                new BookReadDTO(newBook.getId(), newBook.getTitle(), newBook.getGenre(), author.getName() + " " + author.getSurname())
        );
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        Book book = bookService.findById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

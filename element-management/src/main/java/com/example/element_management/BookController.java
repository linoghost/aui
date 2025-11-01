package com.example.element_management;

import com.example.element_management.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/books")
    public List<BookListDTO> getAllBooks() {
        return bookService.findAll().stream()
                .map(b -> new BookListDTO(b.getId(), b.getTitle()))
                .collect(Collectors.toList());
    }

    @GetMapping("/books/authors/{author_id}")
    public ResponseEntity<List<BookListDTO>> getBooksByAuthorID(@PathVariable UUID author_id) {
        //
        //  "KONTYNUACJA" funkcji znajduje się w autorze - komunikacja miedzy portami.
        //
        //pathvariable bierze wartość z naszego id i zamienia ją na uuid
        //responseentity jest stricte na restapi zeby bylo nam łatwo zwracac odpowiedzi (np 200 OK i reszta danych,
        //albo błędy. bez tego CHYBA może się wywalić jak coś pójdzie nie tak
        List<Book> books = bookService.findByAuthorID(author_id);

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build(); // różnica: autor istnieje, ale bez książek
        }

        List<BookListDTO> bookDTOs = books.stream()
                .map(b -> new BookListDTO(b.getId(), b.getTitle()))
                .toList();

        return ResponseEntity.ok(bookDTOs);
    }

    @PostMapping("/authors/{author_id}/books")
    public ResponseEntity<?> addBook(@PathVariable UUID author_id, @RequestBody BookCreateUpdateDTO dto) {

        Book newBook = new Book(UUID.randomUUID(), dto.getTitle(), dto.getGenre(), author_id);

        return ResponseEntity.status(201).body(
                new BookReadDTO(newBook.getId(), newBook.getTitle(), newBook.getGenre())
        );
    }

    @DeleteMapping("/books/{title}")
    public ResponseEntity<Void> deleteBook(@PathVariable String title) {
        Book book = bookService.findByTitle(title);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        UUID id = book.getId();
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

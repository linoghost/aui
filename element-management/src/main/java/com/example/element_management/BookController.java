package com.example.element_management;

import com.example.element_management.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(
    origins = "http://localhost:4200",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)

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

    @GetMapping("/books/authors/{authorId}")
    public ResponseEntity<List<BookListDTO>> getBooksByAuthorID(@PathVariable UUID authorId) {
        //
        //  "KONTYNUACJA" funkcji znajduje się w autorze - komunikacja miedzy portami.
        //
        //pathvariable bierze wartość z naszego id i zamienia ją na uuid
        //responseentity jest stricte na restapi zeby bylo nam łatwo zwracac odpowiedzi (np 200 OK i reszta danych,
        //albo błędy. bez tego CHYBA może się wywalić jak coś pójdzie nie tak
        List<Book> books = bookService.findByAuthorId(authorId);

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build(); // różnica: autor istnieje, ale bez książek
        }

        List<BookListDTO> bookDTOs = books.stream()
                .map(b -> new BookListDTO(b.getId(), b.getTitle()))
                .toList();

        return ResponseEntity.ok(bookDTOs);
    }

    @PostMapping("books/authors/{authorId}")
    public ResponseEntity<?> addBook(@PathVariable UUID authorId, @RequestBody BookCreateUpdateDTO dto) {

        Book newBook = new Book(UUID.randomUUID(), dto.getTitle(), dto.getGenre(), authorId);
        bookService.save(newBook);
        return ResponseEntity.status(201).body(
                new BookReadDTO(newBook.getId(), newBook.getTitle(), newBook.getGenre())
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

    @DeleteMapping("/books/authors/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID authorId){
        List<Book> books = bookService.findByAuthorId(authorId);

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build(); // różnica: autor istnieje, ale bez książek
        }

        UUID id;
        for (Book book : books){
            id=book.getId();
            bookService.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }


}

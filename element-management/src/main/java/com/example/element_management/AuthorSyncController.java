package com.example.element_management;

import com.example.element_management.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/internal/authors")
public class AuthorSyncController {

    private final BookService bookService;

    public AuthorSyncController(BookService bookService) {
        this.bookService = bookService;
    }

    // gdy dodano autora w category-management
    @PostMapping
    public ResponseEntity<Void> handleAuthorAdded(@RequestBody Map<String, Object> authorData) {
        System.out.println("nowy autor: " + authorData);
        
        return ResponseEntity.ok().build();
    }

    
    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> handleAuthorDeleted(@PathVariable UUID authorId) {
        System.out.println("usuwamy autora + authorId");
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


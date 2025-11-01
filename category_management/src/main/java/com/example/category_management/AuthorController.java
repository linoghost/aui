package com.example.category_management;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors") //taki define ścieżki, żeby nie pisać za każdym razem całej
public class AuthorController {
    private final AuthorService authorService;
    private final WebClient webClient = WebClient.create("http://localhost:8082"); //books

    public AuthorController(AuthorService authorService){
        this.authorService=authorService;
    }

    @GetMapping
    public List<AuthorListDTO> getAllAuthors(){
        List<AuthorListDTO> authorlist = authorService.findAll().stream()
                .map(a-> new AuthorListDTO(a.getId(), a.getName() + " " + a.getSurname())).collect(Collectors.toList());

        return authorlist;
    }


    @GetMapping("/{surname}")
    public ResponseEntity<AuthorReadDTO> getAuthor(@PathVariable String surname) { //
        Author author = authorService.findbySurname(surname);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }

        

        AuthorReadDTO dto = new AuthorReadDTO(
                author.getId(),
                author.getName(),
                author.getSurname()
        );

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<AuthorReadDTO> createAuthor(@RequestBody AuthorCreateUpdateDTO dto) {
        Author author = new Author(UUID.randomUUID(), dto.getName(), dto.getSurname());
        authorService.save(author);

        return ResponseEntity.status(201).body(
                new AuthorReadDTO(author.getId(), author.getName(), author.getSurname())
        );
    }


    @PutMapping("/{surname}")
    public ResponseEntity<AuthorReadDTO> updateAuthor(@PathVariable String surname, @RequestBody AuthorCreateUpdateDTO dto) {
        Author existing = authorService.findbySurname(surname);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setName(dto.getName());
        existing.setSurname(dto.getSurname());
        authorService.save(existing);

        

        return ResponseEntity.ok(new AuthorReadDTO(existing.getId(), existing.getName(), existing.getSurname()));
    }


    @DeleteMapping("/{surname}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable String surname) {
        Author author = authorService.findbySurname(surname);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        UUID id = author.getId();

        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{surname}/books")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable String surname){
        Author author = authorService.findbySurname(surname);
        if (author==null){
            return ResponseEntity.notFound().build();
        }

        List<BookListDTO> books = webClient.get()
                .uri("/api/books/authors/{authorId}", author.getId())
                .retrieve()
                .bodyToFlux(BookListDTO.class)
                .collectList()
                .block();
        if (books == null || books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(books);
    }

    @PostMapping("/{surname}/books")
    public ResponseEntity<?> addBook(@PathVariable String surname, @RequestBody BookCreateUpdateDTO dto) {
        
        Author author = authorService.findbySurname(surname);
        if (author == null) {
            return ResponseEntity.notFound().build(); // 404, nie ma takiego autora
        }

        
        BookReadDTO createdBook = webClient.post()
                .uri("/api/authors/{authorId}/books", author.getId())
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(BookReadDTO.class)
                .block();

        if (createdBook == null) {
            return ResponseEntity.internalServerError().build();
        }

        
        return ResponseEntity.status(201).body(createdBook);
    }

}

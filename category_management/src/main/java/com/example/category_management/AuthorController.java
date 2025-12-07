package com.example.category_management;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(
    origins = "http://localhost:4200",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)

@RestController
@RequestMapping("/api/authors") //taki define ścieżki, żeby nie pisać za każdym razem całej
public class AuthorController {
    private final AuthorService authorService;
    private final WebClient webClient; // <--- USUNIĘTE "= WebClient.create(...)"

    // --- POPRAWIONY KONSTRUKTOR ---
    public AuthorController(AuthorService authorService, 
                            WebClient.Builder webClientBuilder,
                            @Value("${element.url:http://localhost:8082}") String elementUrl) {
        
        this.authorService = authorService;
        
        // Logujemy, żebyś widziała w konsoli, z czym się łączy
        System.out.println("🔗 Configuring WebClient for Element Service at: " + elementUrl);
        
        // Budujemy WebClienta używając adresu z Dockera (lub localhosta w IntelliJ)
        this.webClient = webClientBuilder.baseUrl(elementUrl).build();
    }

    @GetMapping
    public List<AuthorListDTO> getAllAuthors(){
        List<AuthorListDTO> authorlist = authorService.findAll().stream()
                .map(a-> new AuthorListDTO(a.getId(), a.getName() + " " + a.getSurname())).collect(Collectors.toList());

        return authorlist;
    }


    @GetMapping("/{id}")
    public ResponseEntity<AuthorReadDTO> getAuthor(@PathVariable UUID id) { //
        Author author = authorService.findById(id);
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
        webClient
        .post()
        .uri("/internal/authors") 
        .bodyValue(Map.of(
            "id", author.getId(),
            "name", author.getName(),
            "surname", author.getSurname()
        ))
        .retrieve()
        .toBodilessEntity()
        .block();
        return ResponseEntity.status(201).body(
                new AuthorReadDTO(author.getId(), author.getName(), author.getSurname())
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<AuthorReadDTO> updateAuthor(@PathVariable UUID id, @RequestBody AuthorCreateUpdateDTO dto) {
        Author existing = authorService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setName(dto.getName());
        existing.setSurname(dto.getSurname());
        authorService.save(existing);

        

        return ResponseEntity.ok(new AuthorReadDTO(existing.getId(), existing.getName(), existing.getSurname()));
    }


    @DeleteMapping("/{id}") //ZMIANA bo już nie wpisujemy zapytań z ręki
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
        System.out.println("aaaa");
        Author author = authorService.findById(id);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        
        webClient.delete().uri("/internal/authors/{authorId}", id).retrieve()
        .toBodilessEntity().block();

        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable UUID id){
        Author author = authorService.findById(id);
        if (author==null){
            return ResponseEntity.notFound().build();
        }

        List<BookListDTO> books = webClient.get()
                .uri("/api/books/authors/{authorId}", id)
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

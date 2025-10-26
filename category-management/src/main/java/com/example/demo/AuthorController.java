package com.example.demo;

import com.example.demo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors") //taki define ścieżki, żeby nie pisać za każdym razem całej
public class AuthorController {
    private final AuthorService authorService;

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


}

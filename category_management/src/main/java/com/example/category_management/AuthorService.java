package com.example.category_management;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;


    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findById(UUID id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Author findbySurname(String surname){
        Author author = authorRepository.findBySurname(surname);
        if (author!=null){
            return author;
        }
        return null;
    }

    public void deleteById(UUID id) {
        authorRepository.deleteById(id);
    }



}

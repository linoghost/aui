package com.example.element_management;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(UUID id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book findByTitle(String title){
        return bookRepository.findByTitle(title);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }

    // tak w razie W żeby było, skoro ma obsługiwać ig
    public List<Book> findByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }
}


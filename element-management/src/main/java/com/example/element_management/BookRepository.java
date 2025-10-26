package com.example.element_management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    // metoda do wyszukiwania książek po autorze
    List<Book> findByAuthorID(UUID author_id);
    Book findByTitle(String title);
}

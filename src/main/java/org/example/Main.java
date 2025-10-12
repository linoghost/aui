import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        Author king = new Author.Builder()
                .id(1)
                .name("Stephen")
                .surname("King")
                .build();

        Author tolkien = new Author.Builder()
                .id(2)
                .name("J.R.R.")
                .surname("Tolkien")
                .build();


        Book b1 = new Book.Builder()
                .id(101)
                .title("It")
                .genre("Horror")
                .author(king)
                .build();

        Book b2 = new Book.Builder()
                .id(102)
                .title("The Shining")
                .genre("Horror")
                .author(king)
                .build();

        Book b3 = new Book.Builder()
                .id(201)
                .title("The Hobbit")
                .genre("Fantasy")
                .author(tolkien)
                .build();

        Book b4 = new Book.Builder()
                .id(202)
                .title("The Lord of the Rings")
                .genre("Fantasy")
                .author(tolkien)
                .build();



        king.addBook(b1);
        king.addBook(b2);

        tolkien.addBook(b3);
        tolkien.addBook(b4);

        List<Author> authors = new ArrayList<>();
        authors.add(king);
        authors.add(tolkien);


        authors.forEach(author -> {
            System.out.println(author);
            author.getBooks().forEach(book -> {
                System.out.println("   ↳ " + book);
            });
            System.out.println();
        });
    }
}

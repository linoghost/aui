import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        List<Author> authors = createAuthorsAndBooks();

        //task 2
        System.out.println("task 2"+'\'');
        authors.forEach(author -> {
            System.out.println(author);
            author.getBooks().forEach(book -> {
                System.out.println(" -- " + book);
            });
            System.out.println();
        });
        System.out.println("\n"+"\n");
        //task 3
        System.out.println("task 3"+'\'');
        Set<Book> allBooks = authors.stream()// zaczynamy strumien
                .flatMap(author -> author.getBooks().stream()) //basically mamy male zbiorki od kazdego autora i je zcalamy
                .collect(Collectors.toSet());//setujemy zeby duplikatow nie bylo

        allBooks.stream()
                .forEach(book -> System.out.println(book));

        System.out.println("\n"+"\n");
        //task 4
        System.out.println("task 4"+'\'');
        allBooks.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase("Fantasy")) // wybieramy tylko fantasy
                .sorted(Comparator.comparing(Book::getTitle))                 // sortujemy po tytule
                .forEach(System.out::println);

        System.out.println("\n"+"\n");
        //task 5
        System.out.println("task 5"+'\'');
        List<BookDTO> bookDTOs = allBooks.stream()
                .map(book->book.toDTO()).sorted()
                .collect(Collectors.toList());

        bookDTOs.stream().forEach(book -> System.out.println(book));

        System.out.println("\n"+"\n");
        //task 6
        // zapis
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("authors.dat"))) {
            out.writeObject(authors);
            System.out.println("saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //odczyt
        List<Author> loadedAuthors = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("authors.dat"))) {
            loadedAuthors = (List<Author>) in.readObject();
            System.out.println("read");
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //print if needed
        if (loadedAuthors != null) {
            loadedAuthors.forEach(System.out::println);
        }


        System.out.println("\n"+"\n");
        //task 7
        System.out.println("task 7"+'\'');
        ForkJoinPool customPool = new ForkJoinPool(6); // 3 wątki

        try {
            customPool.submit(() -> {
                authors.parallelStream().forEach(author -> {
                    System.out.println(Thread.currentThread().getName() +
                            " → Autor: " + author.getName() + " " + author.getSurname());
                    for (Book b : author.getBooks()) {
                        try {
                            Thread.sleep(5000); // wruuum
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        System.out.println("   " + Thread.currentThread().getName() +
                                " → Książka: " + b.getTitle());
                    }
                });
            }).join(); // join() czeka aż wszystko się wykona
        } finally {
            customPool.shutdown(); // ZAMKNIJ POOL po pracy!
        }

        System.out.println("yippiee :)");
    }

    private static List<Author> createAuthorsAndBooks() {
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

        Author rowling = new Author.Builder()
                .id(3)
                .name("J.K.")
                .surname("Rowling")
                .build();

        Author orwell = new Author.Builder()
                .id(4)
                .name("George")
                .surname("Orwell")
                .build();

        Author austen = new Author.Builder()
                .id(5)
                .name("Jane")
                .surname("Austen")
                .build();

        Author herbert = new Author.Builder()
                .id(6)
                .name("Frank")
                .surname("Herbert")
                .build();

        // Książki Stephena Kinga
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
                .id(103)
                .title("Carrie")
                .genre("Horror")
                .author(king)
                .build();

        Book b4 = new Book.Builder()
                .id(104)
                .title("Misery")
                .genre("Psychological Thriller")
                .author(king)
                .build();

        // Książki J.R.R. Tolkiena
        Book b5 = new Book.Builder()
                .id(201)
                .title("The Hobbit")
                .genre("Fantasy")
                .author(tolkien)
                .build();

        Book b6 = new Book.Builder()
                .id(202)
                .title("The Lord of the Rings")
                .genre("Fantasy")
                .author(tolkien)
                .build();

        Book b7 = new Book.Builder()
                .id(203)
                .title("The Silmarillion")
                .genre("Fantasy")
                .author(tolkien)
                .build();

        // Książki J.K. Rowling
        Book b8 = new Book.Builder()
                .id(301)
                .title("Harry Potter and the Philosopher's Stone")
                .genre("Fantasy")
                .author(rowling)
                .build();

        Book b9 = new Book.Builder()
                .id(302)
                .title("Harry Potter and the Chamber of Secrets")
                .genre("Fantasy")
                .author(rowling)
                .build();

        Book b10 = new Book.Builder()
                .id(303)
                .title("Harry Potter and the Prisoner of Azkaban")
                .genre("Fantasy")
                .author(rowling)
                .build();

        // Książki George'a Orwella
        Book b11 = new Book.Builder()
                .id(401)
                .title("1984")
                .genre("Dystopian Fiction")
                .author(orwell)
                .build();

        Book b12 = new Book.Builder()
                .id(402)
                .title("Animal Farm")
                .genre("Political Satire")
                .author(orwell)
                .build();

        // Książki Jane Austen
        Book b13 = new Book.Builder()
                .id(501)
                .title("Pride and Prejudice")
                .genre("Romance")
                .author(austen)
                .build();

        Book b14 = new Book.Builder()
                .id(502)
                .title("Sense and Sensibility")
                .genre("Romance")
                .author(austen)
                .build();

        Book b15 = new Book.Builder()
                .id(503)
                .title("Emma")
                .genre("Romance")
                .author(austen)
                .build();

        // Książki Franka Herberta
        Book b16 = new Book.Builder()
                .id(601)
                .title("Dune")
                .genre("Science Fiction")
                .author(herbert)
                .build();

        Book b17 = new Book.Builder()
                .id(602)
                .title("Dune Messiah")
                .genre("Science Fiction")
                .author(herbert)
                .build();

        Book b18 = new Book.Builder()
                .id(603)
                .title("Children of Dune")
                .genre("Science Fiction")
                .author(herbert)
                .build();

        // Dodawanie książek do autorów
        king.addBook(b1);
        king.addBook(b2);
        king.addBook(b3);
        king.addBook(b4);

        tolkien.addBook(b5);
        tolkien.addBook(b6);
        tolkien.addBook(b7);

        rowling.addBook(b8);
        rowling.addBook(b9);
        rowling.addBook(b10);

        orwell.addBook(b11);
        orwell.addBook(b12);

        austen.addBook(b13);
        austen.addBook(b14);
        austen.addBook(b15);

        herbert.addBook(b16);
        herbert.addBook(b17);
        herbert.addBook(b18);

        List<Author> authors = new ArrayList<>();
        authors.add(king);
        authors.add(tolkien);
        authors.add(rowling);
        authors.add(orwell);
        authors.add(austen);
        authors.add(herbert);

        return authors;
    }
}
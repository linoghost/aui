import java.io.Serializable;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Author implements Comparable<Author>, Serializable {
    private int id;
    private String name;
    private String surname;
    private List<Book> books;

    private Author(Builder builder){
        this.id= builder.id;;
        this.name = builder.name;
        this.surname = builder.surname;
        this.books = builder.books;
    }

    public static class Builder{//jak ktos nie ma ide co mu podpowiada co ustawił
        //jako które w konstruktorze to przydatne
        private int id;
        private String name;
        private String surname;
        private List<Book> books;

        public Builder id(int id){
            this.id=id;
            return this; //do łączenia metod tak jakby
        }
        public Builder name(String name){
            this.name=name;
            return this;
        }
        public Builder surname(String surname){
            this.surname=surname;
            return this;
        }
        public Builder books(List<Book> books){
            this.books=books;
            return this;
        }

        public Author build(){
            return new Author(this);
        }
    }

    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o; //musimy rzutować o na autora bo w argumencie jest jako typ Object
        //a do porównania muszą być tego samego typu
        return id == author.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); //trzeba nadpisać hash żeby faktycznie rozróżniało obiekty bo domyślnie rozróżnia po adresie
    }

    @Override
    public int compareTo(Author other) { //nadpis zeby porównywało po nazwiskach
        return this.surname.compareToIgnoreCase(other.surname);
    }

    @Override
    public String toString(){
        if (books == null || books.isEmpty()) {
            return "Author{id=" + id +
                    ", name='" + name + " " + surname + '\'' +
                    ", books=[]}";
        }

        String titles = "";
        for (Book b : books) {
            titles += b.getTitle() + ", ";
        }

//        if (titles.endsWith(", ")) {
//            titles = titles.substring(0, titles.length() - 2);
//        }

        return "Author{id=" + id +
                ", Name: '" + name + " " + surname + '\'' +
                ", Books: [" + titles + "]}";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
        if (book.getAuthor() != this) {
            book.setAuthor(this); //zabezpieczenie
        }
    }
    public List<Book> getBooks() {
        return books;
    }






}
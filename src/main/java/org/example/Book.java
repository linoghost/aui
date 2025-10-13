import java.io.Serializable;
import java.util.Objects;

public class Book implements Comparable<Book>, Serializable {
    private int id;
    private String title;
    private String genre;
    private Author author;



    private Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.genre = builder.genre;
        this.author = builder.author;
    }


    public static class Builder {
        private int id;
        private String title;
        private String genre;
        private Author author;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder author(Author author) {
            this.author = author;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id == book.id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }



    @Override
    public String toString() {
        return "Book{id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", author='" + (author != null ? author.getName() + " " + author.getSurname() : "none") + '\'' +
                '}';
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author){
        this.author=author;
    }

    public BookDTO toDTO() {
        String authorName;
        if (author != null) {
            authorName = author.getName() + " " + author.getSurname();
        } else {
            authorName = "unknown";
        }

        return new BookDTO(id, title, genre, authorName);
    }

}

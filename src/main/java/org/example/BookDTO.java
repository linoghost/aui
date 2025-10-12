public class BookDTO {//klasa "do druku"
    private int id;
    private String title;
    private String genre;
    private String authorFullName;

    public BookDTO(int id, String title, String genre, String authorFullName) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.authorFullName = authorFullName;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", author='" + authorFullName + '\'' +
                '}';
    }
}

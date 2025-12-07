import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthorsService, Author } from '../../services/authors';
import { BooksService, Book } from '../../services/books';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-author-details',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './author-details.html',
  styleUrls: ['./author-details.css']
})
export class AuthorDetailsComponent implements OnInit {
  author?: Author;
  books: Book[] = [];
  newBook = { title: '', genre: '' };
  showAddForm = false;
  authorId!: string; 
  editingBook: any = null;
  loading = false;
  id!: string;
  selectedBookId: string | null = null;


  constructor(
    private route: ActivatedRoute,
    private authorService: AuthorsService,
    private booksService: BooksService
    
  ) {}

  ngOnInit(): void {
    this.authorId = this.route.snapshot.paramMap.get('id')!;

    if (this.authorId) {
      this.authorService.getAuthorById(this.authorId).subscribe((a) => (this.author = a));
      this.booksService.getBooksByAuthorId(this.authorId).subscribe((b) => (this.books = b));
    }
  }
  

  toggleAddForm(): void {
    this.showAddForm = !this.showAddForm;
  }
  
  toggleBookDetails(bookId: string): void {
  this.selectedBookId = this.selectedBookId === bookId ? null : bookId;
}


  addBook(): void {
    console.log('g', this.newBook);

    if (!this.newBook.title || !this.newBook.genre) {
      console.warn('nie tak cos');
      return;
    }

    if (!this.authorId) {
      console.error('nie ma id autora');
      return;
    }

    this.booksService.addBook(this.authorId, this.newBook).subscribe({
      next: () => {
        this.newBook = { title: '', genre: '' };
        this.showAddForm = false;

        this.booksService.getBooksByAuthorId(this.authorId).subscribe((b) => (this.books = b));
      },
      error: (err) => console.error('cos zle poszlo xd', err),
    });
  }
    
 loadAuthor(): void {

    this.loading = true;
    this.authorService.getAuthorById(this.authorId).subscribe({
      next: (data) => {
        this.author = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading author', err);
        this.loading = false;
      }
    });
  }


  loadBooks(): void {
    this.booksService.getBooksByAuthorId(this.authorId).subscribe({
      next: (data) => (this.books = data),
      error: (err) => console.error('Error loading books', err)
    });


  }

  deleteBook(id: string): void {
    if (confirm('Delete this book?')) {
      this.booksService.deleteBook(id).subscribe({
        next: () => (this.books = this.books.filter(b => b.id !== id)),
        error: (err) => console.error('Error deleting book', err)
      });
    }
  }
    startEditing(book: any): void {
    this.editingBook = { ...book }; // kopia oryginału
    console.log('Edytowana książka:', this.editingBook);
  }

  cancelEditing(): void {
    this.editingBook = null;
  }

  updateBook(): void {
    if (!this.editingBook) return;
    console.log('Updating book', this.editingBook);

    this.booksService.updateBook(this.editingBook.id, {
      title: this.editingBook.title,
      genre: this.editingBook.genre
    }).subscribe({
      next: () => {
        alert('Book updated!');
        this.loadBooks();
        this.editingBook = null;
      },
      error: (err) => console.error('Error updating book', err)
    });
}
}
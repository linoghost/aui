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
  id!: string;
  author?: Author;
  books: Book[] = [];
  loading = false;

  showAddForm = false;
  newBook = { title: '', genre: '' };

  constructor(
    private route: ActivatedRoute,
    private authorsService: AuthorsService,
    private booksService: BooksService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.loadAuthor();
    this.loadBooks();
  }

  loadAuthor(): void {
    this.loading = true;
    this.authorsService.getAuthorById(this.id).subscribe({
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
    this.booksService.getBooksByAuthorId(this.id).subscribe({
      next: (data) => (this.books = data),
      error: (err) => console.error('Error loading books', err)
    });
  }
    toggleAddForm(): void {
    this.showAddForm = !this.showAddForm;
  }

  addBook(): void {
    if (!this.newBook.title || !this.newBook.genre) return;

    this.booksService.addBook(this.id, this.newBook).subscribe({
      next: () => {
        this.loadBooks();
        this.newBook = { title: '', genre: '' };
        this.showAddForm = false;
      },
      error: (err) => console.error('Error adding book', err)
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
}

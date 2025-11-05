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
  authorId!: string; // 👈 tu

  constructor(
    private route: ActivatedRoute,
    private authorService: AuthorsService,
    private booksService: BooksService
  ) {}

  ngOnInit(): void {
    this.authorId = this.route.snapshot.paramMap.get('id')!; // 👈 zapamiętaj id

    if (this.authorId) {
      this.authorService.getAuthorById(this.authorId).subscribe((a) => (this.author = a));
      this.booksService.getBooksByAuthorId(this.authorId).subscribe((b) => (this.books = b));
    }
  }

  toggleAddForm(): void {
    this.showAddForm = !this.showAddForm;
  }

  addBook(): void {
    console.log('🟢 AddBook clicked', this.newBook);

    if (!this.newBook.title || !this.newBook.genre) {
      console.warn('⚠️ Form incomplete');
      return;
    }

    if (!this.authorId) {
      console.error('❌ No author ID');
      return;
    }

    this.booksService.addBook(this.authorId, this.newBook).subscribe({
      next: () => {
        console.log('✅ Book added successfully');
        this.newBook = { title: '', genre: '' };
        this.showAddForm = false;

        // opcjonalnie odśwież listę książek:
        this.booksService.getBooksByAuthorId(this.authorId).subscribe((b) => (this.books = b));
      },
      error: (err) => console.error('🚨 Error adding book', err),
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

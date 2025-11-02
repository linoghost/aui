import { Component, OnInit } from '@angular/core';
import { AuthorsService, Author } from '../../services/authors';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-author-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './author-list.html',
  styleUrls: ['./author-list.css']
})
export class AuthorListComponent implements OnInit {

  authors: Author[] = [];
  loading = false;

  constructor(private authorsService: AuthorsService) { }

  ngOnInit(): void {
    this.loadAuthors();
  }

  loadAuthors(): void {
    this.loading = true;
    this.authorsService.getAuthors().subscribe({
      next: (data) => {
        console.log('Authors response:', data);
        this.authors = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading authors', err);
        this.loading = false;
      }
    });
  }

  deleteAuthor(id: string): void {
    if (confirm('Are you sure you want to delete this author?')) {
      this.authorsService.deleteAuthor(id).subscribe({
        next: () => {
          this.authors = this.authors.filter(a => a.id !== id);
        },
        error: (err) => console.error('Error deleting author', err)
      });
    }
  }
}

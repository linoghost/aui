import { Component, OnInit } from '@angular/core';
import { AuthorsService, Author } from '../../services/authors';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-author-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './author-list.html',
  styleUrls: ['./author-list.css']
})
export class AuthorListComponent implements OnInit {

  authors: Author[] = [];
  
  loading = false;

  newAuthor = {
    name: '',
    surname: ''
  };

  editingAuthorId: string | null = null;
  editedName = '';
  editedSurname = '';

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
  
  createAuthor(): void {
    if (!this.newAuthor.name || !this.newAuthor.surname) return;

    this.authorsService.createAuthor(this.newAuthor).subscribe({
      next: (created) => {
        this.authors.push(created);
        this.newAuthor = { name: '', surname: '' }; // reset formu
      },
      error: (err) => console.error('Error creating author', err)
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
  startEditing(author: Author): void {
    this.editingAuthorId = author.id;
    // Rozdzielamy fullName na imię i nazwisko
    const [first, ...rest] = author.fullName.split(' ');
    this.editedName = first;
    this.editedSurname = rest.join(' ');
  }

  cancelEditing(): void {
    this.editingAuthorId = null;
    this.editedName = '';
    this.editedSurname = '';
  }

  saveAuthor(id: string): void {
  this.authorsService.updateAuthor(id, this.editedName, this.editedSurname).subscribe({
    next: (updatedAuthor) => {
      const index = this.authors.findIndex(a => a.id === id);
      if (index > -1) {
        this.authors[index].fullName = updatedAuthor.fullName;
      }
      this.cancelEditing();
    },
    error: (err) => console.error('Error updating author', err)
  });
}

}

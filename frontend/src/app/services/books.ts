import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Book {
  id: string;
  title: string;
  genre: string;
}

@Injectable({ providedIn: 'root' })
export class BooksService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getBooksByAuthorId(authorId: string): Observable<Book[]> {
  return this.http.get<Book[]>(`http://localhost:8080/api/authors/${authorId}/books`);
  }

  deleteBook(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/books/${id}`);
  }
  
  addBook(authorId: string, book: { title: string; genre: string }): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/books/authors/${authorId}`, book);
  }

  updateBook(bookId: string, updatedBook: { title: string; genre: string }): Observable<void> {
  return this.http.put<void>(`${this.apiUrl}/books/${bookId}`, updatedBook);
  }


}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Author {
  id: string;
  fullName: string;
  name?:string;
  surname?:string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthorsService {

  private apiUrl = '/api/authors'; 

  constructor(private http: HttpClient) { }

  getAuthors(): Observable<Author[]> {
    return this.http.get<Author[]>(this.apiUrl);
  }

  deleteAuthor(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  createAuthor(author: { name: string; surname: string }): Observable<Author> {
  return this.http.post<Author>(this.apiUrl, author);
  }

  getAuthorById(id: string): Observable<Author> {
  return this.http.get<Author>(`${this.apiUrl}/${id}`);
  }


  updateAuthor(id: string, name: string, surname: string): Observable<Author> {
  return this.http.put<Author>(`${this.apiUrl}/${id}`, {
    name: name,
    surname: surname
  });
  
}



}

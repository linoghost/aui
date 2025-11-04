import { Routes } from '@angular/router';
import { AuthorListComponent } from './components/author-list/author-list';
import { AuthorDetailsComponent } from './components/author-details/author-details';

export const routes: Routes = [
  { path: '', redirectTo: 'authors', pathMatch: 'full' },
  { path: 'authors', component: AuthorListComponent },
  { path: 'authors/:id/books', component: AuthorDetailsComponent }

];

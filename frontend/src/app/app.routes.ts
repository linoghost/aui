import { Routes } from '@angular/router';
import { AuthorListComponent } from './components/author-list/author-list';

export const routes: Routes = [
  { path: '', redirectTo: 'authors', pathMatch: 'full' },
  { path: 'authors', component: AuthorListComponent }
];

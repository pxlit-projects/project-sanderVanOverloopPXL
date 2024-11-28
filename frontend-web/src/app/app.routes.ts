import { Routes } from '@angular/router';
import {LoginComponent} from './login/login/login.component';
import {TestpageComponent} from './test/testpage/testpage.component';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'testpage', component: TestpageComponent }

];

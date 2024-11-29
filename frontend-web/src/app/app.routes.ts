import { Routes } from '@angular/router';
import {LoginComponent} from './login/login/login.component';
import {TestpageComponent} from './test/testpage/testpage.component';
import {HomeComponent} from './home/home/home.component';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'testpage', component: TestpageComponent },
  { path: 'home', component: HomeComponent }


];

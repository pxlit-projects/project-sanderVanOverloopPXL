import { Routes } from '@angular/router';
import {LoginComponent} from './login/login/login.component';
import {TestpageComponent} from './test/testpage/testpage.component';
import {HomeComponent} from './home/home/home.component';
import {AddPostComponent} from './add-post/add-post/add-post.component';
import {EditPageComponent} from './editPage/edit-page/edit-page.component';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'testpage', component: TestpageComponent },
  { path: 'home', component: HomeComponent },
  { path: 'add-post', component: AddPostComponent },
  { path: 'edit/:id', component: EditPageComponent }


];

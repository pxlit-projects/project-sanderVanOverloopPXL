import { Routes } from '@angular/router';
import {LoginComponent} from './login/login/login.component';

import {HomeComponent} from './home/home/home.component';
import {AddPostComponent} from './add-post/add-post/add-post.component';
import {EditPageComponent} from './editPage/edit-page/edit-page.component';
import {ReviewPostComponent} from './review-post/review-post.component';
import {NotificationComponent} from './notification/notification.component';
import {PostDetailComponent} from './post-detail/post-detail.component';
import {isLoggedInGuardGuard} from './is-logged-in-guard.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent, canActivate: [isLoggedInGuardGuard] },
  { path: 'add-post', component: AddPostComponent, canActivate: [isLoggedInGuardGuard] },
  { path: 'edit/:id', component: EditPageComponent, canActivate: [isLoggedInGuardGuard] },
  { path: 'review', component: ReviewPostComponent, canActivate: [isLoggedInGuardGuard] },
  { path: 'notification', component: NotificationComponent, canActivate: [isLoggedInGuardGuard] },
  { path: 'post-detail/:id', component: PostDetailComponent, canActivate: [isLoggedInGuardGuard] }


];

// src/app/home/home/home.component.ts
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PostDTO } from '../../services/postdtos';
import { environment } from '../../../environments/environment';
import { CommonModule } from '@angular/common';
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from '@angular/material/card';
import { Router, RouterLink } from '@angular/router';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  standalone: true,
  styleUrls: ['./home.component.css'],
  imports: [CommonModule, MatCard, MatCardActions, RouterLink, MatCardContent, MatCardSubtitle, MatCardTitle, MatCardHeader, MatButton]
})
export class HomeComponent implements OnInit {
  posts: PostDTO[] = [];
  postsInConcept: PostDTO[] = [];
  userRole: string | null = null;
  username: string | null = null;
  userId: number | null = null;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.userRole = localStorage.getItem('userRole');
    this.username = localStorage.getItem('currentUser');
    const userId = localStorage.getItem('userId');
    this.userId = userId ? parseInt(userId, 10) : null;

    if (this.username && this.userRole && this.userId !== null) {
      this.fetchAllPublicPosts();
      this.fetchPostsInConcept();
    } else {
      console.error('User information is missing');
    }
  }

  fetchAllPublicPosts(): void {
    const url = `${environment.postUrl}/allpublic`;
    console.log('Fetching posts from URL:', url);
    this.http.get<PostDTO[]>(url).subscribe(
      (data: PostDTO[]) => {
        this.posts = data;
      },
      (error) => {
        console.error('Error fetching posts', error);
      }
    );
  }

  fetchPostsInConcept(): void {
    const headers = {
      'User': this.username!,
      'Role': this.userRole!,
      'UserId': this.userId!.toString()
    };
    const url = `${environment.postUrl}/inConcept`;
    console.log('Fetching posts in concept from URL:', url);
    this.http.get<PostDTO[]>(url, { headers }).subscribe(
      (data: PostDTO[]) => {
        this.postsInConcept = data;
      },
      (error) => {
        console.error('Error fetching posts in concept', error);
      }
    );
  }

  openEditPage(postId: number): void {
    this.router.navigate(['/edit', postId]);
  }

  isUserRoleUser(): boolean {
    return this.userRole === 'user';
  }
}

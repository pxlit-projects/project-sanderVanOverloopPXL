// src/app/home/home/home.component.ts
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PostDTO } from '../../services/postdtos';
import { environment } from '../../../environments/environment';
import { CommonModule } from '@angular/common';
import { MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle } from '@angular/material/card';
import { Router, RouterLink } from '@angular/router';
import { MatButton } from '@angular/material/button';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { PostService } from '../../services/post-service.service';
import { FilterPostsRequest } from '../../services/postrequests';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  standalone: true,
  styleUrls: ['./home.component.css'],
  imports: [CommonModule, MatCard, MatCardActions, RouterLink, MatCardContent, MatCardSubtitle, MatCardTitle, MatCardHeader, MatButton, ReactiveFormsModule]
})
export class HomeComponent implements OnInit {
  posts: PostDTO[] = [];
  postsInConcept: PostDTO[] = [];
  userRole: string | null = null;
  username: string | null = null;
  userId: number | null = null;
  filterForm: FormGroup;

  constructor(private http: HttpClient, private router: Router, private fb: FormBuilder, private postService: PostService) {
    this.filterForm = this.fb.group({
      content: [''],
      author: [''],
      date: ['']
    });
  }

  ngOnInit(): void {
    this.userRole = localStorage.getItem('userRole');
    this.username = localStorage.getItem('currentUser');
    const userId = localStorage.getItem('userId');
    this.userId = userId ? parseInt(userId, 10) : null;

    if (this.username && this.userRole && this.userId !== null) {
      this.fetchAllPublicPostsAndFilter();
      this.fetchPostsInConcept();
    } else {
      console.error('User information is missing');
    }
  }

  fetchAllPublicPostsAndFilter(): void {
    const content = this.filterForm.get('content')?.value.toLowerCase();
    const author = this.filterForm.get('author')?.value.toLowerCase();
    const date = this.filterForm.get('date')?.value;

    this.http.get<PostDTO[]>(`${environment.postUrl}/allpublic`).subscribe(
      (data: PostDTO[]) => {
        this.posts = data;

        if (!content && !author && !date) {
          return;
        }

        this.posts = this.posts.filter(post => {
          const matchesContent = !content || post.content.toLowerCase().includes(content);
          const matchesAuthor = !author || post.author.toLowerCase().includes(author);
          const matchesDate = !date || post.dateCreated.startsWith(date);
          return matchesContent && matchesAuthor && matchesDate;
        });
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

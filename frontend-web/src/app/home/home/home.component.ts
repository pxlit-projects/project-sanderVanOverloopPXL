import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PostDTO } from '../../services/postdtos';
import { environment } from '../../../environments/environment';
import { CommonModule } from '@angular/common';
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from '@angular/material/card';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  standalone: true,
  styleUrls: ['./home.component.css'],
  imports: [CommonModule, MatCardContent, MatCardSubtitle, MatCardTitle, MatCardHeader, MatCard]
})
export class HomeComponent implements OnInit {
  posts: PostDTO[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
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
}

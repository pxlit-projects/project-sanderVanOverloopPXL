import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PostService } from '../../services/post-service.service';

@Component({
  selector: 'app-testpage',
  templateUrl: './testpage.component.html',
  standalone: true,
  imports: [FormsModule],
  styleUrl: './testpage.component.css'
})
export class TestpageComponent implements OnInit {
  userRole: string | null = null;
  username: string | null = null;
  userId: number | null = null;

  post = {
    title: '',
    content: '',
    dateCreated: '',
    inConcept: false
  };

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.userRole = localStorage.getItem('userRole');
    this.username = localStorage.getItem('currentUser');
    const userId = localStorage.getItem('userId');
    this.userId = userId ? parseInt(userId, 10) : null;
  }

  submitPost(): void {
    this.postService.createPost(this.post, this.userRole || '', this.username || '', this.userId || 0)
      .subscribe(response => {
        console.log('Post created successfully', response);
      }, error => {
        console.error('Error creating post', error);
      });
  }
}

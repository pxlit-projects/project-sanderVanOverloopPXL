import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PostService } from '../../services/post-service.service';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatCheckboxModule, MatButtonModule, CommonModule],
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {
  addPostForm!: FormGroup;
  currentDate: string = new Date().toISOString().slice(0, 16);
  userRole: string | null = null;
  username: string | null = null;
  userId: number | null = null;
  errorMessage: string | null = null;

  constructor(private fb: FormBuilder, private postService: PostService, private router: Router) {}

  ngOnInit(): void {
    this.userRole = localStorage.getItem('userRole');
    this.username = localStorage.getItem('currentUser');
    const userId = localStorage.getItem('userId');
    this.userId = userId ? parseInt(userId, 10) : null;

    this.addPostForm = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required],
      dateCreated: [this.currentDate, Validators.required],
      inConcept: [{ value: true, disabled: true }]
    });
  }

  addPost(): void {
    if (this.addPostForm.valid && this.userRole && this.username && this.userId !== null) {
      console.log('Form is valid, submitting post:', this.addPostForm.getRawValue());
      this.postService.createPost(this.addPostForm.getRawValue(), this.userRole, this.username, this.userId).subscribe(
        () => {
          console.log('Post added successfully');
          this.router.navigate(['/home']);
        },
        (error) => {
          console.error('Error adding post', error);
          this.errorMessage = error.error.message || error.message || 'An error occurred while adding the post. Please try again.';
        }
      );
    } else {
      console.log('Form is invalid or user information is missing');
      this.errorMessage = 'Form is invalid or user information is missing';
    }
  }
}

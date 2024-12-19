import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../../services/post-service.service';
import { EditPostRequest } from '../../services/postrequests';
import { PostDTO } from '../../services/postdtos';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-edit-page',
  templateUrl: './edit-page.component.html',
  standalone: true,
  styleUrls: ['./edit-page.component.css'],
  imports: [CommonModule, MatFormFieldModule, MatInputModule, MatCheckboxModule, MatButtonModule, ReactiveFormsModule]
})
export class EditPageComponent implements OnInit {
  editPostForm!: FormGroup;
  postId!: number;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private postService: PostService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.editPostForm = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required],
      dateCreated: [new Date().toISOString().slice(0, 16), Validators.required],
      inConcept: [{ value: false, disabled: true }]
    });

    this.postId = +this.route.snapshot.paramMap.get('id')!;
    this.postService.getPostById(this.postId).subscribe(
      (post: PostDTO) => {
        this.editPostForm.patchValue({
          title: post.title,
          content: post.content,
          dateCreated: new Date().toISOString().slice(0, 16),
          inConcept: post.inConcept
        });
        this.editPostForm.get('inConcept')?.disable();
      },
      (error) => {
        console.error('Error fetching post', error);
        this.errorMessage = 'Error fetching post details';
      }
    );
  }

  updatePost(): void {
    if (this.editPostForm.valid) {
      const updatedPost: EditPostRequest = this.editPostForm.value;
      this.postService.updatePost(this.postId, updatedPost, 'author', 'username', 'userId').subscribe(
        () => {
          this.router.navigate(['/home']);
        },
        (error) => {
          console.error('Error updating post', error);
          this.errorMessage = error.error.message || error.message || 'An error occurred while updating the post. Please try again.';
        }
      );
    }
  }
}

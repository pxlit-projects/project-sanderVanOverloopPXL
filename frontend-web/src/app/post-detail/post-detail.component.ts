import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../services/post-service.service';
import { CommentService } from '../services/comment.service';
import { PostDTO } from '../services/postdtos';
import { CommentDTO } from '../services/commentdtos';
import { AddCommentRequest, EditCommentRequest } from '../services/commentrequests';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class PostDetailComponent implements OnInit {
  post: PostDTO | null = null;
  comments: CommentDTO[] = [];
  newCommentMessage: string = '';
  editCommentMessage: string = '';
  userRole: string | null = null;
  username: string | null = null;
  userId: number | null = null;
  editingCommentId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private commentService: CommentService
  ) {}

  ngOnInit(): void {
    this.userRole = localStorage.getItem('userRole');
    this.username = localStorage.getItem('currentUser');
    const userId = localStorage.getItem('userId');
    this.userId = userId ? parseInt(userId, 10) : null;

    const postId = this.route.snapshot.paramMap.get('id');
    if (postId) {
      this.postService.getPostById(+postId).subscribe(
        (data: PostDTO) => {
          this.post = data;
          this.loadComments(+postId);
        },
        (error) => {
          console.error('Error fetching post details', error);
        }
      );
    }
  }

  loadComments(postId: number): void {
    this.commentService.getComments(postId).subscribe(
      (data: CommentDTO[]) => {
        this.comments = data;
      },
      (error) => {
        console.error('Error fetching comments', error);
      }
    );
  }

  addComment(): void {
    if (this.post) {
      const request = new AddCommentRequest(this.newCommentMessage, this.post.id);
      this.commentService.addComment(request, this.userRole!, this.username!, this.userId!.toString()).subscribe(
        () => {
          this.loadComments(this.post!.id);
          this.newCommentMessage = '';
        },
        (error) => {
          console.error('Error adding comment', error);
        }
      );
    }
  }

  startEditing(comment: CommentDTO): void {
    if (comment.usernameMadeBy === this.username) {
      this.editingCommentId = comment.id;
      this.editCommentMessage = comment.message;
    } else {
      alert('You can only edit your own comments.');
    }
  }

  deleteComment(commentId: number): void {
    this.commentService.deleteComment(commentId, this.userRole!, this.username!, this.userId!.toString()).subscribe(
      () => {
        this.loadComments(this.post!.id);
      },
      (error) => {
        console.error('Error deleting comment', error);
      }
    );
  }

  saveEditComment(commentId: number): void {
    const request = new EditCommentRequest(this.editCommentMessage);
    this.commentService.editComment(commentId, request, this.userRole!, this.username!, this.userId!.toString()).subscribe(
      () => {
        this.loadComments(this.post!.id);
        this.editingCommentId = null;
        this.editCommentMessage = '';
      },
      (error) => {
        console.error('Error editing comment', error);
      }
    );
  }

  cancelEdit(): void {
    this.editingCommentId = null;
    this.editCommentMessage = '';
  }

  isCommentOwner(comment: CommentDTO): boolean {
    return comment.usernameMadeBy === this.username;
  }
}

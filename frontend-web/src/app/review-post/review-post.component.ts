import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../services/review.service';
import { PostDTO } from '../services/reviewdtos';
import {ReviewStatus} from '../services/reviewstatus';
import {ReviewRequest} from '../services/reviewrequests';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-review-post',
  templateUrl: './review-post.component.html',
  standalone: true,
  imports: [
    FormsModule
  ],
  styleUrls: ['./review-post.component.css']
})
export class ReviewPostComponent implements OnInit {
  reviewsInWait: PostDTO[] = [];
  userRole: string | null = null;
  username: string | null = null;
  userId: number | null = null;

  constructor(private reviewService: ReviewService) {}

  ngOnInit(): void {
    this.userRole = localStorage.getItem('userRole');
    this.username = localStorage.getItem('currentUser');
    const userId = localStorage.getItem('userId');
    this.userId = userId ? parseInt(userId, 10) : null;

    if (this.username && this.userRole && this.userId !== null) {
      this.getReviewsInWait();
    } else {
      console.error('User information is missing');
    }
  }

  getReviewsInWait(): void {
    if (this.userRole && this.username && this.userId !== null) {
      this.reviewService.getReviewsInWait(this.userRole, this.username, this.userId.toString()).subscribe(
        (reviews) => {
          this.reviewsInWait = reviews;
          console.log(reviews);
        },
        (error) => {
          console.error('Error fetching reviews in wait:', error);
        }
      );
    } else {
      console.error('User information is missing');
    }
  }

  acceptReview(reviewId: number): void {
    const reviewRequest = new ReviewRequest(reviewId.toString(), ReviewStatus.APPROVED, 'Review accepted');
    this.reviewService.postReview(reviewRequest, this.userRole!).subscribe(
      () => {
        console.log('Review accepted successfully');
        this.getReviewsInWait(); // Refresh the list of reviews in wait
      },
      (error) => {
        console.error('Error accepting review:', error);
      }
    );
  }

  rejectReview(reviewId: number, rejectionReason: string): void {
    const reviewRequest = new ReviewRequest(reviewId.toString(), ReviewStatus.REJECTED, rejectionReason);
    this.reviewService.postReview(reviewRequest, this.userRole!).subscribe(
      () => {
        console.log('Review rejected successfully');
        this.getReviewsInWait(); // Refresh the list of reviews in wait
      },
      (error) => {
        console.error('Error rejecting review:', error);
      }
    );
  }
}

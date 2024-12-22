import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReviewPostComponent } from './review-post.component';
import { ReviewService } from '../services/review.service';
import { of, throwError } from 'rxjs';
import { PostDTO } from '../services/reviewdtos';
import { ReviewRequest } from '../services/reviewrequests';
import { ReviewStatus } from '../services/reviewstatus';
import { FormsModule } from '@angular/forms';

describe('ReviewPostComponent', () => {
  let component: ReviewPostComponent;
  let fixture: ComponentFixture<ReviewPostComponent>;
  let reviewService: jasmine.SpyObj<ReviewService>;

  beforeEach(async () => {
    const reviewServiceSpy = jasmine.createSpyObj('ReviewService', ['getReviewsInWait', 'postReview']);

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, FormsModule, ReviewPostComponent],
      providers: [{ provide: ReviewService, useValue: reviewServiceSpy }]
    }).compileComponents();

    fixture = TestBed.createComponent(ReviewPostComponent);
    component = fixture.componentInstance;
    reviewService = TestBed.inject(ReviewService) as jasmine.SpyObj<ReviewService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch reviews in wait on init', fakeAsync(() => {
    const reviews: PostDTO[] = [{
      id: 1,
      authorId: 1,
      title: 'Test Review',
      content: 'Test Content',
      author: 'Author',
      dateCreated: new Date().toISOString(),
      inConcept: false,
      isApproved: false,
      inReview: true,
      rejectedReason: ''
    }];
    reviewService.getReviewsInWait.and.returnValue(of(reviews));
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'userId') return '1';
      if (key === 'userRole') return 'admin';
      if (key === 'currentUser') return 'testUser';
      return null;
    });

    component.ngOnInit();
    tick();

    expect(reviewService.getReviewsInWait).toHaveBeenCalledWith('admin', 'testUser', '1');
    expect(component.reviewsInWait).toEqual(reviews);
  }));

  it('should handle error when fetching reviews in wait', fakeAsync(() => {
    reviewService.getReviewsInWait.and.returnValue(throwError('Error fetching reviews'));
    spyOn(console, 'error');
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'userId') return '1';
      if (key === 'userRole') return 'admin';
      if (key === 'currentUser') return 'testUser';
      return null;
    });

    component.ngOnInit();
    tick();

    expect(reviewService.getReviewsInWait).toHaveBeenCalledWith('admin', 'testUser', '1');
    expect(console.error).toHaveBeenCalledWith('Error fetching reviews in wait:', 'Error fetching reviews');
  }));

  it('should accept a review', fakeAsync(() => {
    const reviews: PostDTO[] = [{
      id: 1,
      authorId: 1,
      title: 'Test Review',
      content: 'Test Content',
      author: 'Author',
      dateCreated: new Date().toISOString(),
      inConcept: false,
      isApproved: false,
      inReview: true,
      rejectedReason: ''
    }];
    reviewService.getReviewsInWait.and.returnValue(of(reviews));
    reviewService.postReview.and.returnValue(of(undefined));
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'userId') return '1';
      if (key === 'userRole') return 'admin';
      if (key === 'currentUser') return 'testUser';
      return null;
    });

    component.ngOnInit();
    tick();

    component.acceptReview(1);
    tick();

    expect(reviewService.postReview).toHaveBeenCalledWith(jasmine.any(ReviewRequest), 'admin');
  }));

  it('should handle error when accepting a review', fakeAsync(() => {
    const reviews: PostDTO[] = [{
      id: 1,
      authorId: 1,
      title: 'Test Review',
      content: 'Test Content',
      author: 'Author',
      dateCreated: new Date().toISOString(),
      inConcept: false,
      isApproved: false,
      inReview: true,
      rejectedReason: ''
    }];
    reviewService.getReviewsInWait.and.returnValue(of(reviews));
    reviewService.postReview.and.returnValue(throwError('Error accepting review'));
    spyOn(console, 'error');
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'userId') return '1';
      if (key === 'userRole') return 'admin';
      if (key === 'currentUser') return 'testUser';
      return null;
    });

    component.ngOnInit();
    tick();

    component.acceptReview(1);
    tick();

    expect(reviewService.postReview).toHaveBeenCalledWith(jasmine.any(ReviewRequest), 'admin');
    expect(console.error).toHaveBeenCalledWith('Error accepting review:', 'Error accepting review');
  }));

  it('should reject a review', fakeAsync(() => {
    const reviews: PostDTO[] = [{
      id: 1,
      authorId: 1,
      title: 'Test Review',
      content: 'Test Content',
      author: 'Author',
      dateCreated: new Date().toISOString(),
      inConcept: false,
      isApproved: false,
      inReview: true,
      rejectedReason: ''
    }];
    reviewService.getReviewsInWait.and.returnValue(of(reviews));
    reviewService.postReview.and.returnValue(of(undefined));
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'userId') return '1';
      if (key === 'userRole') return 'admin';
      if (key === 'currentUser') return 'testUser';
      return null;
    });

    component.ngOnInit();
    tick();

    component.rejectReview(1, 'Rejection reason');
    tick();

    expect(reviewService.postReview).toHaveBeenCalledWith(jasmine.any(ReviewRequest), 'admin');
  }));

  it('should handle error when rejecting a review', fakeAsync(() => {
    const reviews: PostDTO[] = [{
      id: 1,
      authorId: 1,
      title: 'Test Review',
      content: 'Test Content',
      author: 'Author',
      dateCreated: new Date().toISOString(),
      inConcept: false,
      isApproved: false,
      inReview: true,
      rejectedReason: ''
    }];
    reviewService.getReviewsInWait.and.returnValue(of(reviews));
    reviewService.postReview.and.returnValue(throwError('Error rejecting review'));
    spyOn(console, 'error');
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'userId') return '1';
      if (key === 'userRole') return 'admin';
      if (key === 'currentUser') return 'testUser';
      return null;
    });

    component.ngOnInit();
    tick();

    component.rejectReview(1, 'Rejection reason');
    tick();

    expect(reviewService.postReview).toHaveBeenCalledWith(jasmine.any(ReviewRequest), 'admin');
    expect(console.error).toHaveBeenCalledWith('Error rejecting review:', 'Error rejecting review');
  }));
});

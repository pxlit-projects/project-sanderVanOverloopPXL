import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HomeComponent } from './home.component';
import { RouterTestingModule } from '@angular/router/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { PostService } from '../../services/post-service.service';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { PostDTO } from '../../services/postdtos';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let postService: PostService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule, ReactiveFormsModule, HomeComponent],
      providers: [PostService]
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    postService = TestBed.inject(PostService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form with default values', () => {
    expect(component.filterForm).toBeDefined();
    expect(component.filterForm.get('content')?.value).toBe('');
    expect(component.filterForm.get('author')?.value).toBe('');
    expect(component.filterForm.get('date')?.value).toBe('');
  });



  it('should handle error when fetching public posts fails', () => {
    spyOn(postService, 'getAllPublicPosts').and.returnValue(throwError({ error: { message: 'Error' } }));

    component.fetchAllPublicPostsAndFilter();

    expect(component.posts.length).toBe(0);
  });




  it('should handle error when fetching posts in concept fails', () => {
    spyOn(postService, 'getPostsInConcept').and.returnValue(throwError({ error: { message: 'Error' } }));

    component.fetchPostsInConcept();

    expect(component.postsInConcept.length).toBe(0);
  });

  it('should navigate to edit page', () => {
    spyOn(router, 'navigate');

    component.openEditPage(1);

    expect(router.navigate).toHaveBeenCalledWith(['/edit', 1]);
  });

  it('should navigate to review page', () => {
    spyOn(router, 'navigate');

    component.goToReview();

    expect(router.navigate).toHaveBeenCalledWith(['/review']);
  });

  it('should navigate to detail page', () => {
    spyOn(router, 'navigate');

    component.goToDetail(1);

    expect(router.navigate).toHaveBeenCalledWith(['/post-detail', 1]);
  });




  it('should handle error when sending post to review fails', () => {
    const post: PostDTO = { id: 1, title: 'Test Title', content: 'Test Content', inConcept: true, authorId: 1, author: 'Author', dateCreated: new Date().toISOString(), isApproved: false, inReview: false, rejectedReason: '' };
    component.postsInConcept = [post];
    spyOn(postService, 'sendForReview').and.returnValue(throwError({ error: { message: 'Error' } }));

    component.sendToReview(1);

    expect(component.postsInConcept.length).toBe(1);
  });
});

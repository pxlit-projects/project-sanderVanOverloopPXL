import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EditPageComponent } from '../../editPage/edit-page/edit-page.component';
import { PostService } from '../../services/post-service.service';
import { of, throwError } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { PostDTO } from '../../services/postdtos';

describe('EditPageComponent', () => {
  let component: EditPageComponent;
  let fixture: ComponentFixture<EditPageComponent>;
  let postService: PostService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        RouterTestingModule,
        HttpClientTestingModule,
        BrowserAnimationsModule,
        EditPageComponent
      ],
      providers: [
        PostService,
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: () => '1' } }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(EditPageComponent);
    component = fixture.componentInstance;
    postService = TestBed.inject(PostService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form with default values', () => {
    expect(component.editPostForm).toBeDefined();
    expect(component.editPostForm.get('title')?.value).toBe('');
    expect(component.editPostForm.get('content')?.value).toBe('');
    expect(component.editPostForm.get('dateCreated')?.value).toBe(new Date().toISOString().slice(0, 16));
    expect(component.editPostForm.get('inConcept')?.value).toBe(false);
  });

  it('should fetch post details and populate the form', () => {
    const post: PostDTO = {
      id: 1,
      title: 'Test Title',
      content: 'Test Content',
      inConcept: true,
      authorId: 1,
      author: 'Author',
      dateCreated: new Date().toISOString(),
      isApproved: false,
      inReview: false,
      rejectedReason: ''
    };
    spyOn(postService, 'getPostById').and.returnValue(of(post));

    component.ngOnInit();

    expect(postService.getPostById).toHaveBeenCalledWith(1);
    expect(component.editPostForm.get('title')?.value).toBe('Test Title');
    expect(component.editPostForm.get('content')?.value).toBe('Test Content');
    expect(component.editPostForm.get('inConcept')?.value).toBe(true);
  });

  it('should display an error message if fetching post details fails', () => {
    spyOn(postService, 'getPostById').and.returnValue(throwError({ error: { message: 'Error' } }));

    component.ngOnInit();

    expect(component.errorMessage).toBe('Error fetching post details');
  });

  it('should call postService.updatePost and navigate to /home on successful post update', () => {
    spyOn(postService, 'updatePost').and.returnValue(of(void 0));
    spyOn(router, 'navigate');

    component.editPostForm.get('title')?.setValue('Updated Title');
    component.editPostForm.get('content')?.setValue('Updated Content');

    component.updatePost();

    expect(postService.updatePost).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/home']);
  });

  it('should display an error message if post update fails', () => {
    spyOn(postService, 'updatePost').and.returnValue(throwError({ error: { message: 'Error' } }));

    component.editPostForm.get('title')?.setValue('Updated Title');
    component.editPostForm.get('content')?.setValue('Updated Content');

    component.updatePost();

    expect(component.errorMessage).toBe('Error');
  });
});

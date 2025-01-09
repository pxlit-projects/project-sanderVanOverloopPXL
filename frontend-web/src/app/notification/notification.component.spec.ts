import { TestBed, ComponentFixture, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NotificationComponent } from './notification.component';
import { PostService } from '../services/post-service.service';
import { of, throwError } from 'rxjs';
import { NotificationDTO } from '../services/postdtos';

describe('NotificationComponent', () => {
  let component: NotificationComponent;
  let fixture: ComponentFixture<NotificationComponent>;
  let postService: jasmine.SpyObj<PostService>;

  beforeEach(async () => {
    const postServiceSpy = jasmine.createSpyObj('PostService', ['getNotifications']);

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, NotificationComponent],
      providers: [{ provide: PostService, useValue: postServiceSpy }]
    }).compileComponents();

    fixture = TestBed.createComponent(NotificationComponent);
    component = fixture.componentInstance;
    postService = TestBed.inject(PostService) as jasmine.SpyObj<PostService>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch notifications on init', fakeAsync(() => {
    const notifications: NotificationDTO[] = [{ content: 'Test Notification' }];
    postService.getNotifications.and.returnValue(of(notifications));
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'userId') return '1';
      if (key === 'userRole') return 'admin';
      return null;
    });

    component.ngOnInit();

    expect(postService.getNotifications).toHaveBeenCalledWith('1', 'admin');
    expect(component.notifications).toEqual(notifications);
  }));

  it('should handle error when fetching notifications', fakeAsync(() => {
    postService.getNotifications.and.returnValue(throwError('Error fetching notifications'));
    spyOn(localStorage, 'getItem').and.callFake((key: string) => {
      if (key === 'userId') return '1';
      if (key === 'userRole') return 'admin';
      return null;
    });
    spyOn(console, 'error');

    component.ngOnInit();

    expect(postService.getNotifications).toHaveBeenCalledWith('1', 'admin');
    expect(console.error).toHaveBeenCalledWith('Error fetching notifications', 'Error fetching notifications');
  }));

  it('should log error if user information is missing', () => {
    spyOn(localStorage, 'getItem').and.returnValue(null);
    spyOn(console, 'error');

    component.fetchNotifications();

    expect(console.error).toHaveBeenCalledWith('User information is missing');
  });
});

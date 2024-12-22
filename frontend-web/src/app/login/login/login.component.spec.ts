import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginComponent } from './login.component';
import { FormsModule } from '@angular/forms';
import { of } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: jasmine.SpyObj<AuthService>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    const authServiceSpy = jasmine.createSpyObj('AuthService', ['login']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [FormsModule, LoginComponent],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should login successfully', () => {
    authService.login.and.returnValue(true);
    component.user = 'admin';
    component.password = 'admin123';

    component.handleLogin();

    expect(authService.login).toHaveBeenCalledWith('admin', 'admin123');
    expect(router.navigate).toHaveBeenCalledWith(['/home']);
    expect(component.errorMessage).toBeNull();
  });

  it('should fail login with incorrect credentials', () => {
    authService.login.and.returnValue(false);
    component.user = 'wrongUser';
    component.password = 'wrongPassword';

    component.handleLogin();

    expect(authService.login).toHaveBeenCalledWith('wrongUser', 'wrongPassword');
    expect(router.navigate).not.toHaveBeenCalled();
    expect(component.errorMessage).toBe('Login failed. Please check your credentials and try again.');
  });

  it('should display error message on failed login', () => {
    authService.login.and.returnValue(false);
    component.user = 'user';
    component.password = 'wrongPassword';

    component.handleLogin();

    fixture.detectChanges();

    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('.alert-danger').textContent).toContain('Login failed. Please check your credentials and try again.');
  });
});

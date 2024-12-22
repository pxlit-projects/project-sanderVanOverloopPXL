import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login successfully with valid credentials', () => {
    const result = service.login('admin', 'admin123');
    expect(result).toBeTrue();
    expect(localStorage.getItem('authToken')).toBe('true');
    expect(localStorage.getItem('userRole')).toBe('author');
    expect(localStorage.getItem('currentUser')).toBe('admin');
    expect(localStorage.getItem('userId')).toBe('1');
  });



  it('should logout successfully', () => {
    service.login('admin', 'admin123');
    service.logout();
    expect(localStorage.getItem('authToken')).toBeNull();
    expect(localStorage.getItem('userRole')).toBeNull();
    expect(localStorage.getItem('currentUser')).toBeNull();
    expect(localStorage.getItem('userId')).toBeNull();
  });

  it('should return the correct username', () => {
    service.login('admin', 'admin123');
    expect(service.getUsername()).toBe('admin');
  });

  it('should return the correct role', () => {
    service.login('admin', 'admin123');
    expect(service.getRole()).toBe('author');
  });

  it('should return the correct userId', () => {
    service.login('admin', 'admin123');
    expect(service.getUserId()).toBe(1);
  });

  it('should return true if authenticated', () => {
    service.login('admin', 'admin123');
    expect(service.isAuthenticated()).toBeTrue();
  });

  it('should return false if not authenticated', () => {
    service.logout();
    expect(service.isAuthenticated()).toBeFalse();
  });
});

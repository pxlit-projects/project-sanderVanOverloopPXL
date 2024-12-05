import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private accounts = [
    { userId: 1, username: 'admin', password: 'admin123', role: 'author' },
    { userId: 2, username: 'user', password: 'user123', role: 'user' },
    { userId: 3, username: 'editor', password: 'editor123', role: 'editor' }
  ];

  private currentUser: string | null = null;
  private currentRole: string = 'user';
  private currentUserId: number | null = null;

  constructor() {}

  login(username: string, password: string): boolean {
    const account = this.accounts.find(
      (acc) => acc.username === username && acc.password === password
    );

    if (account) {
      this.currentUser = account.username;
      this.currentRole = account.role;
      this.currentUserId = account.userId;

      // Set authToken, userId, and username in localStorage to simulate authentication
      localStorage.setItem('authToken', 'true');
      localStorage.setItem('userRole', account.role);
      localStorage.setItem('currentUser', account.username);
      localStorage.setItem('userId', account.userId.toString());

      return true;
    }

    return false; // Return false if login fails
  }

  logout(): void {
    this.currentUser = null;
    this.currentRole = 'user';
    this.currentUserId = null;

    // Clear authToken, userId, and username from localStorage
    localStorage.removeItem('authToken');
    localStorage.removeItem('userRole');
    localStorage.removeItem('currentUser');
    localStorage.removeItem('userId');
  }

  getUsername(): string | null {
    return localStorage.getItem('currentUser'); // Retrieve the username from localStorage
  }

  getRole(): string {
    return localStorage.getItem('userRole') || 'user';
  }

  getUserId(): number | null {
    const userId = localStorage.getItem('userId');
    return userId ? parseInt(userId, 10) : null;
  }

  isAuthenticated(): boolean {
    return localStorage.getItem('authToken') === 'true';
  }
}

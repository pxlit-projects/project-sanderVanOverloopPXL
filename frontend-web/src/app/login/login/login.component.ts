import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  user: string = '';
  password: string = '';
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  handleLogin(): void {
    const success = this.authService.login(this.user, this.password);
    if (success) {
      this.router.navigate(['/testpage']);
    } else {
      this.errorMessage = 'Login failed. Please check your credentials and try again.';
    }
  }
}

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

  constructor(private authService: AuthService, private router: Router) {}

  handleLogin(): void {
    const success = this.authService.login(this.user, this.password);
    if (success) {
      // Redirect to the testpage
      this.router.navigate(['/testpage']);
    } else {
      // Handle login failure
      console.log('Login failed');
    }
  }
}

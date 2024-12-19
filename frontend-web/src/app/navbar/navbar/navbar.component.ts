import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {Location, NgIf} from '@angular/common';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  imports: [

  ],
  standalone: true
})
export class NavbarComponent {
  userRole: string = 'user'; // Replace with actual logic to get the user role

  constructor(private router: Router, private location: Location) {}

  goBack(): void {
    this.location.back();
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  goToNotifications(): void {
    this.router.navigate(['/notification']);
  }




}

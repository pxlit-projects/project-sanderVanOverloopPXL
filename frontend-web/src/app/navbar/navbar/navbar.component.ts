import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  standalone: true
})
export class NavbarComponent {
  constructor(private router: Router, private location: Location) {}

  goBack(): void {
    this.location.back();
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}

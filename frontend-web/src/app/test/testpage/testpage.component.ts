import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-testpage',
  templateUrl: './testpage.component.html',
  standalone: true,
  imports: [FormsModule],
  styleUrl: './testpage.component.css'
})
export class TestpageComponent implements OnInit {
  userRole: string | null = null;
  username: string | null = null;

  post = {
    title: '',
    content: '',
    dateCreated: '',
    inConcept: false
  };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.userRole = localStorage.getItem('userRole');
    this.username = localStorage.getItem('currentUser'); // Ensure this matches the key used in AuthService
  }

  submitPost(): void {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Role': this.userRole || '',
      'User': this.username || ''
    });

    this.http.post(`${environment.apiUrl}/post`, this.post, { headers })
      .subscribe(response => {
        console.log('Post created successfully', response);
      }, error => {
        console.error('Error creating post', error);
      });
  }
}

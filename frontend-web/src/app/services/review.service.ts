import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ReviewRequest } from './reviewrequests';
import { PostDTO } from './reviewdtos';

@Injectable({
  providedIn: 'root',
})
export class ReviewService {
  private apiUrl = `${environment.reviewApiUrl}`;

  constructor(private http: HttpClient) {}

  getReviewsInWait(userRole: string, user: string, userId: string): Observable<PostDTO[]> {
    const headers = new HttpHeaders({
      'Role': userRole,
      'User': user,
      'Userid': userId.toString(),
    });
    return this.http.get<PostDTO[]>(this.apiUrl, { headers });
  }

  postReview(review: ReviewRequest, userRole: string): Observable<void> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Role': userRole,
    });
    return this.http.post<void>(this.apiUrl, review, { headers });
  }
}

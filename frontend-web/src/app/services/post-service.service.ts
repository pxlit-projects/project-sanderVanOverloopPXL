import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { PostRequest, EditPostRequest, ApplyForReviewRequest, AddNotificationRequest, FilterPostsRequest } from './postrequests';
import { PostDTO, NotificationDTO } from './postdtos';

@Injectable({
  providedIn: 'root',
})
export class PostService {

  constructor(private http: HttpClient) {}

  createPost(post: PostRequest, userRole: string, username: string, userId: number): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Role': userRole,
      'User': username,
      'UserId': userId.toString(),
    });
    return this.http.post(`${environment.postUrl}`, post, { headers });
  }

  getPostsInConcept(user: string, userRole: string, userId: string): Observable<PostDTO[]> {
    const headers = new HttpHeaders({
      'User': user,
      'Role': userRole,
      'UserId': userId,
    });
    return this.http.get<PostDTO[]>(`${environment.postUrl}/inConcept`, { headers });
  }

  updatePost(id: number, request: EditPostRequest, userRole: string, user: string, userId: string): Observable<void> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Role': userRole,
      'User': user,
      'UserId': userId,
    });
    return this.http.put<void>(`${environment.postUrl}/${id}`, request, { headers });
  }

  getPostById(id: number): Observable<PostDTO> {
    return this.http.get<PostDTO>(`${environment.postUrl}/${id}`);
  }

  getAllPublicPosts(): Observable<PostDTO[]> {
    const url = `${environment.postUrl}/allpublic`;
    console.log('Fetching posts from URL:', url);
    return this.http.get<PostDTO[]>(url);
  }

  filterPosts(request: FilterPostsRequest): Observable<PostDTO[]> {
    console.log(request);
    return this.http.post<PostDTO[]>(`${environment.postUrl}/filter`, request);
  }

  sendForReview(request: ApplyForReviewRequest, userRole: string, user: string, userId: string): Observable<void> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Role': userRole,
      'User': user,
      'UserId': userId,
    });
    return this.http.post<void>(`${environment.postUrl}/review`, request, { headers });
  }

  addNotification(request: AddNotificationRequest, userRole: string): Observable<void> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Role': userRole,
    });
    return this.http.post<void>(`${environment.postUrl}/add/notification`, request, { headers });
  }

  getNotifications(userId: string, userRole: string): Observable<NotificationDTO[]> {
    const headers = new HttpHeaders({
      'UserId': userId,
      'Role': userRole,
    });
    return this.http.get<NotificationDTO[]>(`${environment.postUrl}/notifications`, { headers });
  }
}

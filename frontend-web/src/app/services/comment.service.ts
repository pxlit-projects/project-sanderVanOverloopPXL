import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { AddCommentRequest, EditCommentRequest } from './commentrequests';
import { CommentDTO } from './commentdtos';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private apiUrl = `${environment.commentApiUrl}`;

  constructor(private http: HttpClient) {}

  addComment(request: AddCommentRequest, userRole: string, username: string, userId: string): Observable<void> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Role': userRole,
      'User': username,
      'Userid': userId,
    });
    return this.http.post<void>(this.apiUrl, request, { headers });
  }

  getComments(postId: number): Observable<CommentDTO[]> {
    return this.http.get<CommentDTO[]>(`${this.apiUrl}/${postId}`);
  }

  editComment(commentId: number, request: EditCommentRequest, userRole: string, username: string, userId: string): Observable<void> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Role': userRole,
      'User': username,
      'Userid': userId,
    });
    return this.http.post<void>(`${this.apiUrl}/comment/${commentId}`, request, { headers });
  }

  deleteComment(commentId: number, userRole: string, username: string, userId: string): Observable<void> {
    const headers = new HttpHeaders({
      'Role': userRole,
      'User': username,
      'Userid': userId,
    });
    return this.http.delete<void>(`${this.apiUrl}/comment/${commentId}`, { headers });
  }
}

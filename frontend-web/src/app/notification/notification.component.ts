import { Component, OnInit } from '@angular/core';
import { PostService } from '../services/post-service.service';
import { NotificationDTO } from '../services/postdtos';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  standalone: true,
  imports: [],
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {
  notifications: NotificationDTO[] = [];

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.fetchNotifications();
  }

  fetchNotifications(): void {
    const userId = localStorage.getItem('userId');
    const userRole = localStorage.getItem('userRole');
    if (userId && userRole) {
      this.postService.getNotifications(userId, userRole).subscribe(
        (data: NotificationDTO[]) => {
          this.notifications = data;
        },
        (error) => {
          console.error('Error fetching notifications', error);
        }
      );
    } else {
      console.error('User information is missing');
    }
  }
}

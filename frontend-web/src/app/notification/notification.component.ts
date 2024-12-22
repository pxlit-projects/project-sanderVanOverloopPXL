import { Component, OnInit, OnDestroy } from '@angular/core';
import { PostService } from '../services/post-service.service';
import { NotificationDTO } from '../services/postdtos';
import { Subscription, interval } from 'rxjs';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit, OnDestroy {
  notifications: NotificationDTO[] = [];
  subscription: Subscription | null = null;

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.fetchNotifications();
    this.subscription = interval(5000).subscribe(() => this.fetchNotifications());
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  fetchNotifications(): void {
    const userId = localStorage.getItem('userId');
    const userRole = localStorage.getItem('userRole');
    if (userId && userRole) {
      this.postService.getNotifications(userId, userRole).subscribe(
        (data: NotificationDTO[]) => {
          this.notifications = data;
          console.log('hey')
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

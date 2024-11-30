package be.pxl.services.repository;

import be.pxl.services.domain.Notification;
import be.pxl.services.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findNotificationByUserId(long userId);
}

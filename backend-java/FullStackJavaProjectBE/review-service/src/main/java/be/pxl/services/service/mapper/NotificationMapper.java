package be.pxl.services.service.mapper;

import be.pxl.services.controllers.dto.NotificationDTO;
import be.pxl.services.domain.Notification;

public class NotificationMapper {
    public static NotificationDTO mapToNotificationDTO(Notification notification) {
        return new NotificationDTO(notification.getMessage());
    }
}

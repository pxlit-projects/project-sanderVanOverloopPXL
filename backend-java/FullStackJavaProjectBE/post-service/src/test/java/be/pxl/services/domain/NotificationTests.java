package be.pxl.services.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotificationTests {
    @Test
    public void testNotificationBuilder() {
        Notification notification = Notification.builder()
                .id(1L)
                .authorId(123L)
                .content("Test content")
                .build();

        assertEquals(1L, notification.getId());
        assertEquals(123L, notification.getAuthorId());
        assertEquals("Test content", notification.getContent());
    }

    @Test
    public void testNotificationNoArgsConstructor() {
        Notification notification = new Notification();

        assertNotNull(notification);
    }

    @Test
    public void testNotificationAllArgsConstructor() {
        Notification notification = new Notification(1L, 123L, "Test content");

        assertEquals(1L, notification.getId());
        assertEquals(123L, notification.getAuthorId());
        assertEquals("Test content", notification.getContent());
    }

    @Test
    public void testNotificationSettersAndGetters() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setAuthorId(123L);
        notification.setContent("Test content");

        assertEquals(1L, notification.getId());
        assertEquals(123L, notification.getAuthorId());
        assertEquals("Test content", notification.getContent());
    }
}

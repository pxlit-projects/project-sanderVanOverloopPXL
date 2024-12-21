package be.pxl.services.domain;

import be.pxl.services.controller.dto.NotificationDTO;
import be.pxl.services.controller.dto.PostDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DTOTests {
    @Test
    public void testNotificationDTO() {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setContent("Test Content");
        assertEquals("Test Content", notificationDTO.getContent());
    }

    @Test
    public void testPostDTO() {
        LocalDateTime now = LocalDateTime.now();
        PostDTO postDTO = new PostDTO(1L, 2L, "Test Title", "Test Content", "Test Author", now, true, false, true, "Test Reason");

        assertEquals(1L, postDTO.getId());
        assertEquals(2L, postDTO.getAuthorId());
        assertEquals("Test Title", postDTO.getTitle());
        assertEquals("Test Content", postDTO.getContent());
        assertEquals("Test Author", postDTO.getAuthor());
        assertEquals(now, postDTO.getDateCreated());
        assertTrue(postDTO.isInConcept());
        assertFalse(postDTO.isApproved());
        assertTrue(postDTO.isInReview());
        assertEquals("Test Reason", postDTO.getRejectedReason());

        postDTO.setId(3L);
        postDTO.setAuthorId(4L);
        postDTO.setTitle("Updated Title");
        postDTO.setContent("Updated Content");
        postDTO.setAuthor("Updated Author");
        postDTO.setDateCreated(now.minusDays(1));
        postDTO.setInConcept(false);
        postDTO.setApproved(true);
        postDTO.setInReview(false);
        postDTO.setRejectedReason("Updated Reason");

        assertEquals(3L, postDTO.getId());
        assertEquals(4L, postDTO.getAuthorId());
        assertEquals("Updated Title", postDTO.getTitle());
        assertEquals("Updated Content", postDTO.getContent());
        assertEquals("Updated Author", postDTO.getAuthor());
        assertEquals(now.minusDays(1), postDTO.getDateCreated());
        assertFalse(postDTO.isInConcept());
        assertTrue(postDTO.isApproved());
        assertFalse(postDTO.isInReview());
        assertEquals("Updated Reason", postDTO.getRejectedReason());
    }
}

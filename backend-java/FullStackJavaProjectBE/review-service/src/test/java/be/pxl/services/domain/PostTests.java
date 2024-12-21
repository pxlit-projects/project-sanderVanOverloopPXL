package be.pxl.services.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostTests {

    @Test
    public void testPost() {
        LocalDateTime now = LocalDateTime.now();
        Post post = new Post(1L, 2L, "Title", "Content", "Author", now, true, false, true, "Rejected Reason");

        assertEquals(1L, post.getId());
        assertEquals(2L, post.getAuthorId());
        assertEquals("Title", post.getTitle());
        assertEquals("Content", post.getContent());
        assertEquals("Author", post.getAuthor());
        assertEquals(now, post.getDateCreated());
        assertTrue(post.isInConcept());
        assertTrue(post.isInReview());
        assertEquals("Rejected Reason", post.getRejectedReason());
        assertEquals(false, post.isApproved());
    }

    @Test
    public void testPostDefaultValues() {
        Post post = new Post();
        assertTrue(post.isInConcept());
        assertEquals(false, post.isApproved());
        assertEquals("", post.getRejectedReason());
    }
}

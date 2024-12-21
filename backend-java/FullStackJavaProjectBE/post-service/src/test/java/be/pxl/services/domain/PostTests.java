package be.pxl.services.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PostTests {
    @Test
    public void testPostBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Post post = Post.builder()
                .id(1L)
                .authorId(123L)
                .title("Test Title")
                .content("Test Content")
                .author("Test Author")
                .dateCreated(now)
                .inConcept(true)
                .isApproved(false)
                .inReview(false)
                .rejectedReason("Test Reason")
                .build();

        assertEquals(1L, post.getId());
        assertEquals(123L, post.getAuthorId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getContent());
        assertEquals("Test Author", post.getAuthor());
        assertEquals(now, post.getDateCreated());
        assertTrue(post.isInConcept());
        assertFalse(post.isApproved());
        assertFalse(post.isInReview());
        assertEquals("Test Reason", post.getRejectedReason());
    }

    @Test
    public void testPostNoArgsConstructor() {
        Post post = new Post();

        assertNotNull(post);
    }

    @Test
    public void testPostAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Post post = new Post(1L, 123L, "Test Title", "Test Content", "Test Author", now, true, false, false, "Test Reason");

        assertEquals(1L, post.getId());
        assertEquals(123L, post.getAuthorId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getContent());
        assertEquals("Test Author", post.getAuthor());
        assertEquals(now, post.getDateCreated());
        assertTrue(post.isInConcept());
        assertFalse(post.isApproved());
        assertFalse(post.isInReview());
        assertEquals("Test Reason", post.getRejectedReason());
    }

    @Test
    public void testPostSettersAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Post post = new Post();
        post.setId(1L);
        post.setAuthorId(123L);
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setAuthor("Test Author");
        post.setDateCreated(now);
        post.setInConcept(true);
        post.setApproved(false);
        post.setInReview(false);
        post.setRejectedReason("Test Reason");

        assertEquals(1L, post.getId());
        assertEquals(123L, post.getAuthorId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getContent());
        assertEquals("Test Author", post.getAuthor());
        assertEquals(now, post.getDateCreated());
        assertTrue(post.isInConcept());
        assertFalse(post.isApproved());
        assertFalse(post.isInReview());
        assertEquals("Test Reason", post.getRejectedReason());
    }
}

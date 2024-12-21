package be.pxl.services.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommentTests {
    @Test
    void testCommentBuilder() {
        Comment comment = Comment.builder()
                .id(1L)
                .message("Test message")
                .userId(1L)
                .usernameMadeBy("testUser")
                .postId(1L)
                .dateCreated(LocalDate.now())
                .build();

        assertNotNull(comment);
        assertEquals(1L, comment.getId());
        assertEquals("Test message", comment.getMessage());
        assertEquals(1L, comment.getUserId());
        assertEquals("testUser", comment.getUsernameMadeBy());
        assertEquals(1L, comment.getPostId());
        assertEquals(LocalDate.now(), comment.getDateCreated());
    }

    @Test
    void testCommentSettersAndGetters() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setMessage("Test message");
        comment.setUserId(1L);
        comment.setUsernameMadeBy("testUser");
        comment.setPostId(1L);
        comment.setDateCreated(LocalDate.now());

        assertEquals(1L, comment.getId());
        assertEquals("Test message", comment.getMessage());
        assertEquals(1L, comment.getUserId());
        assertEquals("testUser", comment.getUsernameMadeBy());
        assertEquals(1L, comment.getPostId());
        assertEquals(LocalDate.now(), comment.getDateCreated());
    }

    @Test
    void testCommentNoArgsConstructor() {
        Comment comment = new Comment();
        assertNotNull(comment);
    }

    @Test
    void testCommentAllArgsConstructor() {
        Comment comment = new Comment(1L, "Test message", 1L, "testUser", 1L, LocalDate.now());
        assertNotNull(comment);
        assertEquals(1L, comment.getId());
        assertEquals("Test message", comment.getMessage());
        assertEquals(1L, comment.getUserId());
        assertEquals("testUser", comment.getUsernameMadeBy());
        assertEquals(1L, comment.getPostId());
        assertEquals(LocalDate.now(), comment.getDateCreated());
    }
}

package be.pxl.services.domain;

import be.pxl.services.controller.Requests.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class RequestTests {
    @Test
    public void testAddCommentRequest() {
        AddCommentRequest request = new AddCommentRequest("Test message", 1L);
        assertEquals("Test message", request.getMessage());
        assertEquals(1L, request.getPostId());

        request.setMessage("Updated message");
        request.setPostId(2L);
        assertEquals("Updated message", request.getMessage());
        assertEquals(2L, request.getPostId());
    }

    @Test
    public void testAddNotificationRequest() {
        AddNotificationRequest request = new AddNotificationRequest(1L, "Test content");
        assertEquals(1L, request.getAuthorId());
        assertEquals("Test content", request.getContent());

        request.setAuthorId(2L);
        request.setContent("Updated content");
        assertEquals(2L, request.getAuthorId());
        assertEquals("Updated content", request.getContent());
    }

    @Test
    public void testApplyForReviewRequest() {
        ApplyForReviewRequest request = new ApplyForReviewRequest(1L, 1L, "Test title", "Test content", "Test author", LocalDateTime.now(), true, false, true, "Test reason");
        assertEquals(1L, request.getId());
        assertEquals(1L, request.getAuthorId());
        assertEquals("Test title", request.getTitle());
        assertEquals("Test content", request.getContent());
        assertEquals("Test author", request.getAuthor());
        assertTrue(request.isInConcept());
        assertFalse(request.isApproved());
        assertTrue(request.isInReview());
        assertEquals("Test reason", request.getRejectedReason());
    }

    @Test
    public void testApplyForReviewRequestBus() {
        ApplyForReviewRequestBus requestBus = new ApplyForReviewRequestBus(1L, 1L, "Test title", "Test content", "Test author", LocalDateTime.now(), true, false, true, "Test reason");
        assertEquals(1L, requestBus.id());
        assertEquals(1L, requestBus.authorId());
        assertEquals("Test title", requestBus.title());
        assertEquals("Test content", requestBus.content());
        assertEquals("Test author", requestBus.author());
        assertTrue(requestBus.inConcept());
        assertFalse(requestBus.isApproved());
        assertTrue(requestBus.inReview());
        assertEquals("Test reason", requestBus.rejectedReason());
    }

    @Test
    public void testEditPostRequest() {
        EditPostRequest request = new EditPostRequest("Test title", "Test content", LocalDateTime.now(), true);
        assertEquals("Test title", request.getTitle());
        assertEquals("Test content", request.getContent());
        assertNotNull(request.getDateCreated());
        assertTrue(request.isInConcept());

        request.setTitle("Updated title");
        request.setContent("Updated content");
        request.setDateCreated(LocalDateTime.now().minusDays(1));
        request.setInConcept(false);
        assertEquals("Updated title", request.getTitle());
        assertEquals("Updated content", request.getContent());
        assertNotNull(request.getDateCreated());
        assertFalse(request.isInConcept());
    }

    @Test
    public void testFilterPostsRequest() {
        FilterPostsRequest request = new FilterPostsRequest("Test content", "Test author", LocalDateTime.now());
        assertEquals("Test content", request.getContent());
        assertEquals("Test author", request.getAuthor());
        assertNotNull(request.getDate());

        request.setContent("Updated content");
        request.setAuthor("Updated author");
        request.setDate(LocalDateTime.now().minusDays(1));
        assertEquals("Updated content", request.getContent());
        assertEquals("Updated author", request.getAuthor());
        assertNotNull(request.getDate());
    }
}

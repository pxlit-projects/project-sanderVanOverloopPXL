package be.pxl.services.domain;

import be.pxl.services.controllers.requests.AddNotificationRequest;
import be.pxl.services.controllers.requests.ApplyForReviewRequestBus;
import be.pxl.services.controllers.requests.ReviewRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTests {
    @Test
    public void testReviewRequest() {
        ReviewRequest reviewRequest = new ReviewRequest("postId", ReviewStatus.REJECTED, "description");

        assertEquals("postId", reviewRequest.postId());
        assertEquals(ReviewStatus.REJECTED, reviewRequest.status());
        assertEquals("description", reviewRequest.description());
    }

    @Test
    public void testAddNotificationRequest() {
        AddNotificationRequest addNotificationRequest = new AddNotificationRequest(1L, "content");

        assertEquals(1L, addNotificationRequest.getAuthorId());
        assertEquals("content", addNotificationRequest.getContent());
    }

    @Test
    public void testAddNotificationRequestDefaultConstructor() {
        AddNotificationRequest addNotificationRequest = new AddNotificationRequest();
        addNotificationRequest.setAuthorId(1L);
        addNotificationRequest.setContent("content");

        assertEquals(1L, addNotificationRequest.getAuthorId());
        assertEquals("content", addNotificationRequest.getContent());
    }

    @Test
    public void testApplyForReviewRequestBus() {
        LocalDateTime now = LocalDateTime.now();
        ApplyForReviewRequestBus applyForReviewRequestBus = new ApplyForReviewRequestBus(
                1L, 2L, "title", "content", "author", now, true, false, true, "rejectedReason");

        assertEquals(1L, applyForReviewRequestBus.id());
        assertEquals(2L, applyForReviewRequestBus.authorId());
        assertEquals("title", applyForReviewRequestBus.title());
        assertEquals("content", applyForReviewRequestBus.content());
        assertEquals("author", applyForReviewRequestBus.author());
        assertEquals(now, applyForReviewRequestBus.dateCreated());
        assertEquals(true, applyForReviewRequestBus.inConcept());
        assertEquals(false, applyForReviewRequestBus.isApproved());
        assertEquals(true, applyForReviewRequestBus.inReview());
        assertEquals("rejectedReason", applyForReviewRequestBus.rejectedReason());
    }
}

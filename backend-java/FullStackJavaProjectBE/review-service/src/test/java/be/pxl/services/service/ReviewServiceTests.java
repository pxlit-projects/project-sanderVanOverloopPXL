package be.pxl.services.service;

import be.pxl.services.Client.PostClient;
import be.pxl.services.controllers.dto.PostDTO;
import be.pxl.services.controllers.requests.AddNotificationRequest;
import be.pxl.services.controllers.requests.ApplyForReviewRequestBus;
import be.pxl.services.controllers.requests.ReviewRequest;
import be.pxl.services.domain.Post;
import be.pxl.services.domain.ReviewStatus;
import be.pxl.services.exceptions.ReviewException;
import be.pxl.services.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ReviewServiceTests {
    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostClient postClient;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostReview() {
        ReviewRequest reviewRequest = new ReviewRequest("1", ReviewStatus.APPROVED, "description");
        Post post = new Post(1L, 2L, "Title", "Content", "Author", LocalDateTime.now(), true, false, true, "Rejected Reason");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        reviewService.postReview(reviewRequest, "editor");

        verify(postRepository, times(1)).delete(post);
        verify(postClient, times(1)).addNotification(any(AddNotificationRequest.class), eq("reviewer"));
        verify(rabbitTemplate, times(1)).convertAndSend(eq("reviewQueue1"), eq(reviewRequest));
    }

    @Test
    public void testPostReviewInvalidPostId() {
        ReviewRequest reviewRequest = new ReviewRequest(null, ReviewStatus.APPROVED, "description");

        ReviewException exception = assertThrows(ReviewException.class, () -> {
            reviewService.postReview(reviewRequest, "editor");
        });

        assertEquals("Post id is null", exception.getMessage());
    }

    @Test
    public void testPostReviewInvalidStatus() {
        ReviewRequest reviewRequest = new ReviewRequest("1", null, "description");

        ReviewException exception = assertThrows(ReviewException.class, () -> {
            reviewService.postReview(reviewRequest, "editor");
        });

        assertEquals("Review is null", exception.getMessage());
    }

    @Test
    public void testPostReviewInvalidUserRole() {
        ReviewRequest reviewRequest = new ReviewRequest("1", ReviewStatus.APPROVED, "description");

        ReviewException exception = assertThrows(ReviewException.class, () -> {
            reviewService.postReview(reviewRequest, "user");
        });

        assertEquals("User is not authorized to review a post", exception.getMessage());
    }

    @Test
    public void testGetReviewsInWait() {
        Post post = new Post(1L, 2L, "Title", "Content", "Author", LocalDateTime.now(), true, false, true, "Rejected Reason");
        when(postRepository.findPostByisApproved(false)).thenReturn(Collections.singletonList(post));

        List<PostDTO> reviewsInWait = reviewService.getReviewsInWait("editor", "user", "1");

        assertEquals(1, reviewsInWait.size());
        assertEquals(post.getId(), reviewsInWait.get(0).getId());
    }

    @Test
    public void testGetReviewsInWaitInvalidUserRole() {
        ReviewException exception = assertThrows(ReviewException.class, () -> {
            reviewService.getReviewsInWait("user", "user", "1");
        });

        assertEquals("User is not authorized to get reviews in wait", exception.getMessage());
    }

    @Test
    public void testListen() {
        ApplyForReviewRequestBus requestBus = new ApplyForReviewRequestBus(1L, 2L, "title", "content", "author", LocalDateTime.now(), true, false, true, "rejectedReason");
        Post post = new Post(1L, 2L, "title", "content", "author", LocalDateTime.now(), true, false, true, "rejectedReason");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        reviewService.listen(requestBus);

        verify(postRepository, times(1)).delete(post);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testListenNullRequest() {
        ReviewException exception = assertThrows(ReviewException.class, () -> {
            reviewService.listen(null);
        });

        assertEquals("Request is null", exception.getMessage());
    }
}

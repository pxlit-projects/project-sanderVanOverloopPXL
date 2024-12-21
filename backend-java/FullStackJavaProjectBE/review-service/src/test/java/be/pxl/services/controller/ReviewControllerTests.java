package be.pxl.services.controller;

import be.pxl.services.controllers.ReviewController;
import be.pxl.services.controllers.dto.PostDTO;
import be.pxl.services.controllers.requests.ReviewRequest;
import be.pxl.services.domain.ReviewStatus;
import be.pxl.services.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ReviewControllerTests {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetReviewsInWait() {
        List<PostDTO> postDTOList = Collections.singletonList(new PostDTO());
        when(reviewService.getReviewsInWait(anyString(), anyString(), anyString())).thenReturn(postDTOList);

        ResponseEntity<List<PostDTO>> response = reviewController.getReviewsInWait("role", "user", "userId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postDTOList, response.getBody());
        verify(reviewService, times(1)).getReviewsInWait("role", "user", "userId");
    }

    @Test
    public void testPostReview() {
        ReviewRequest reviewRequest = new ReviewRequest("postId", ReviewStatus.REJECTED, "description");

        ResponseEntity<Void> response = reviewController.postReview(reviewRequest, "role");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reviewService, times(1)).postReview(reviewRequest, "role");
    }
}

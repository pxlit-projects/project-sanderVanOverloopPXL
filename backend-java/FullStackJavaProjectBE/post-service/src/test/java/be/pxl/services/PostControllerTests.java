package be.pxl.services;

import be.pxl.services.controller.PostController;
import be.pxl.services.controller.Requests.*;
import be.pxl.services.controller.dto.NotificationDTO;
import be.pxl.services.controller.dto.PostDTO;
import be.pxl.services.services.PostService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PostControllerTests {
    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddPost() {
        PostRequest request = new PostRequest();
        doNothing().when(postService).addPost(any(), anyString(), anyString(), anyString());

        ResponseEntity<Void> response = postController.addPost(request, "ROLE_USER", "username", "userId");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(postService, times(1)).addPost(any(), anyString(), anyString(), anyString());
    }

    @Test
    public void testGetPostsInConcept() {
        when(postService.getPostsInConcept(anyString(), anyString(), anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<PostDTO>> response = postController.getPostsInConcept("user", "ROLE_USER", "userId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(postService, times(1)).getPostsInConcept(anyString(), anyString(), anyString());
    }

    @Test
    public void testUpdatePost() {
        EditPostRequest request = new EditPostRequest();
        doNothing().when(postService).updatePost(anyLong(), any(), anyString(), anyString(), anyString());

        ResponseEntity<Void> response = postController.updatePost(1L, request, "ROLE_USER", "user", "userId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(postService, times(1)).updatePost(anyLong(), any(), anyString(), anyString(), anyString());
    }

    @Test
    public void testGetPostById() {
        PostDTO postDTO = new PostDTO();
        when(postService.getPostById(anyLong())).thenReturn(postDTO);

        ResponseEntity<PostDTO> response = postController.getPostById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postDTO, response.getBody());
        verify(postService, times(1)).getPostById(anyLong());
    }

    @Test
    public void testGetAllPublicPosts() {
        when(postService.getAllPublicPosts()).thenReturn(Collections.emptyList());

        ResponseEntity<List<PostDTO>> response = postController.getAllPublicPosts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(postService, times(1)).getAllPublicPosts();
    }

    @Test
    public void testFilterPosts() {
        FilterPostsRequest request = new FilterPostsRequest();
        when(postService.filterPosts(any())).thenReturn(Collections.emptyList());

        ResponseEntity<List<PostDTO>> response = postController.filterPosts(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(postService, times(1)).filterPosts(any());
    }

    @Test
    public void testSendForReview() {
        ApplyForReviewRequest request = new ApplyForReviewRequest();
        doNothing().when(postService).sendForReview(any(), anyString(), anyString(), anyString());

        ResponseEntity<PostDTO> response = postController.sendForReview(request, "ROLE_USER", "user", "userId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(postService, times(1)).sendForReview(any(), anyString(), anyString(), anyString());
    }

    @Test
    public void testAddNotification() {
        AddNotificationRequest request = new AddNotificationRequest();
        doNothing().when(postService).addNotification(any(), anyString());

        ResponseEntity<Void> response = postController.addNotification(request, "ROLE_USER");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(postService, times(1)).addNotification(any(), anyString());
    }

    @Test
    public void testGetNotifications() {
        when(postService.getNotifications(anyString(), anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<NotificationDTO>> response = postController.getNotifications("userId", "ROLE_USER");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(postService, times(1)).getNotifications(anyString(), anyString());
    }
}

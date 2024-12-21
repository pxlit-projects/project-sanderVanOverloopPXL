package be.pxl.services.Services;

import be.pxl.services.Exceptions.PostsException;
import be.pxl.services.controller.Requests.*;
import be.pxl.services.controller.dto.NotificationDTO;
import be.pxl.services.controller.dto.PostDTO;
import be.pxl.services.domain.Notification;
import be.pxl.services.domain.Post;
import be.pxl.services.domain.ReviewStatus;
import be.pxl.services.repository.NotificationRepository;
import be.pxl.services.repository.PostRepository;
import be.pxl.services.services.PostService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PostServiceTests {
    @Mock
    private PostRepository postRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddPost() {
        PostRequest request = new PostRequest();
        request.setTitle("Test Title");
        request.setContent("Test Content");
        request.setDateCreated(LocalDateTime.now());
        request.setInConcept(true);

        when(postRepository.save(any(Post.class))).thenReturn(new Post());

        postService.addPost(request, "author", "Test Author", "1");

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testGetPostsInConcept() {
        Post post = new Post();
        post.setInConcept(true);
        post.setAuthorId(1L);

        when(postRepository.findPostByInConceptAndAuthorId(true, 1L)).thenReturn(Collections.singletonList(post));

        List<PostDTO> posts = postService.getPostsInConcept("Test Author", "author", "1");

        assertEquals(1, posts.size());
        verify(postRepository, times(1)).findPostByInConceptAndAuthorId(true, 1L);
    }

    @Test
    public void testUpdatePost() {
        EditPostRequest request = new EditPostRequest();
        request.setTitle("Updated Title");
        request.setContent("Updated Content");
        request.setDateCreated(LocalDateTime.now());
        request.setInConcept(true);

        Post post = new Post();
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        postService.updatePost(1L, request, "author", "Test Author", "1");

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testGetPostById() {
        Post post = new Post();
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        PostDTO postDTO = postService.getPostById(1L);

        assertNotNull(postDTO);
        verify(postRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetAllPublicPosts() {
        when(postRepository.findPostByisApproved(true)).thenReturn(Collections.emptyList());

        List<PostDTO> posts = postService.getAllPublicPosts();

        assertEquals(0, posts.size());
        verify(postRepository, times(1)).findPostByisApproved(true);
    }

    @Test
    public void testFilterPostss() {
        FilterPostsRequest request = new FilterPostsRequest();
        when(postRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(PostsException.class, () -> postService.filterPosts(request));
    }

    @Test
    public void testSendForReview() {
        ApplyForReviewRequest request = new ApplyForReviewRequest();
        request.setId(1L);

        Post post = new Post();
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        postService.sendForReview(request, "author", "Test Author", "1");

        verify(postRepository, times(1)).save(any(Post.class));

    }

    @Test
    public void testAddNotification() {
        AddNotificationRequest request = new AddNotificationRequest();
        request.setAuthorId(1L);
        request.setContent("Test Content");

        when(notificationRepository.save(any(Notification.class))).thenReturn(new Notification());

        postService.addNotification(request, "reviewer");

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    public void testGetNotifications() {
        Notification notification = new Notification();
        when(notificationRepository.findNotificationsByAuthorId(anyLong())).thenReturn(Collections.singletonList(notification));

        List<NotificationDTO> notifications = postService.getNotifications("1", "author");

        assertEquals(1, notifications.size());
        verify(notificationRepository, times(1)).findNotificationsByAuthorId(anyLong());
    }

    @Test
    public void testAddPostUnauthorized() {
        PostRequest request = new PostRequest();
        PostsException exception = assertThrows(PostsException.class, () -> {
            postService.addPost(request, "user", "Test User", "1");
        });
        assertEquals("User is not authorized to add a post", exception.getMessage());
    }

    @Test
    public void testGetPostsInConceptNoPosts() {
        when(postRepository.findPostByInConceptAndAuthorId(true, 1L)).thenReturn(Collections.emptyList());
        PostsException exception = assertThrows(PostsException.class, () -> {
            postService.getPostsInConcept("Test User", "author", "1");
        });
        assertEquals("No posts in concept", exception.getMessage());
    }

    @Test
    public void testUpdatePostUnauthorized() {
        EditPostRequest request = new EditPostRequest();
        PostsException exception = assertThrows(PostsException.class, () -> {
            postService.updatePost(1L, request, "user", "Test User", "1");
        });
        assertEquals("User is not authorized to update a post", exception.getMessage());
    }


    @Test
    public void testSendForReviewUnauthorized() {
        ApplyForReviewRequest request = new ApplyForReviewRequest();
        PostsException exception = assertThrows(PostsException.class, () -> {
            postService.sendForReview(request, "user", "Test User", "1");
        });
        assertEquals("User is not authorized to send a post for review", exception.getMessage());
    }

    @Test
    public void testAddNotificationUnauthorized() {
        AddNotificationRequest request = new AddNotificationRequest();
        PostsException exception = assertThrows(PostsException.class, () -> {
            postService.addNotification(request, "user");
        });
        assertEquals("User is not authorized to add a notification", exception.getMessage());
    }

    @Test
    public void testGetNotificationsUnauthorized() {
        PostsException exception = assertThrows(PostsException.class, () -> {
            postService.getNotifications("1", "admin");
        });
        assertEquals("User is not authorized to get a notification", exception.getMessage());
    }

    @Test
    public void testReceiveReview() {
        ReviewRequest requestBus = new ReviewRequest("1", ReviewStatus.REJECTED, "Test Reason");
        Post post = new Post();
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        postService.receiveReview(requestBus);

        assertEquals("Test Reason", post.getRejectedReason());
        assertFalse(post.isApproved());
        assertTrue(post.isInConcept());
        assertFalse(post.isInReview());
        verify(postRepository, times(1)).save(post);
    }
    @Test
    public void testFilterPosts() {
        FilterPostsRequest request = new FilterPostsRequest("Test Content", "Test Author", LocalDateTime.now());
        Post post = new Post();
        post.setContent("Test Content");
        post.setAuthor("Test Author");
        post.setDateCreated(LocalDateTime.now());
        when(postRepository.findAll()).thenReturn(Collections.singletonList(post));

        List<PostDTO> result = postService.filterPosts(request);
        assertEquals(1, result.size());
    }

    @Test
    public void testUpdatePostStatus() {
        ReviewRequest requestBus = new ReviewRequest("1", ReviewStatus.REJECTED, "Test Reason");
        Post post = new Post();
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        postService.receiveReview(requestBus);

        assertEquals("Test Reason", post.getRejectedReason());
        assertFalse(post.isApproved());
        assertTrue(post.isInConcept());
        assertFalse(post.isInReview());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    public void testFilterPostsWithNullValues() {
        FilterPostsRequest request = new FilterPostsRequest(null, null, null);
        Post post = new Post();
        post.setContent("Test Content");
        post.setAuthor("Test Author");
        post.setDateCreated(LocalDateTime.now());
        when(postRepository.findAll()).thenReturn(Collections.singletonList(post));

        List<PostDTO> result = postService.filterPosts(request);
        assertEquals(1, result.size());
    }
}

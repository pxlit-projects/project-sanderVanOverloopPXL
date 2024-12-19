package be.pxl.services.services;

import be.pxl.services.Exceptions.PostsException;
import be.pxl.services.controller.Requests.*;
import be.pxl.services.controller.dto.NotificationDTO;
import be.pxl.services.controller.dto.PostDTO;
import be.pxl.services.domain.Notification;
import be.pxl.services.domain.Post;
import be.pxl.services.domain.ReviewStatus;
import be.pxl.services.repository.NotificationRepository;
import be.pxl.services.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {


    private final PostRepository postRepository;
    private final RabbitTemplate rabbitTemplate;
    private final NotificationRepository notificationRepository;

    @Override
    public void addPost(PostRequest post, String userRole, String user, String userId) {
        if (!userRole.equals("author")) {
            throw new PostsException("User is not authorized to add a post");
        }
        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setAuthor(user);
        newPost.setDateCreated(post.getDateCreated());
        newPost.setAuthorId(Long.parseLong(userId));
        newPost.setInConcept(post.isInConcept()); // Set the in_concept field
        postRepository.save(newPost);
    }

    @Override
    public List<PostDTO> getPostsInConcept(String user, String userRole, String userId) {
        List<Post> posts = postRepository.findPostByInConceptAndAuthorId(true, Long.parseLong(userId));
        if (posts.isEmpty()) {
            throw new PostsException("No posts in concept");
        }

        return posts.stream()
                .map(this::mapToPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updatePost(long id, @Valid EditPostRequest request, String userRole, String user, String userId) {
        if (!userRole.equals("author")) {
            throw new PostsException("User is not authorized to update a post");
        }
        Post post = postRepository.findById(id).orElseThrow(() -> new PostsException("Post not found"));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setInConcept(request.isInConcept());
        post.setDateCreated(request.getDateCreated());
        postRepository.save(post);
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostsException("Post not found"));
        return mapToPostDTO(post);
    }

    @Override
    public List<PostDTO> getAllPublicPosts() {
        List<Post> posts = postRepository.findPostByisApproved(true);
        return posts.stream()
                .map(this::mapToPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> filterPosts(FilterPostsRequest request) {
        List<Post> allPost = postRepository.findAll();
        if (allPost.isEmpty()) {
            throw new PostsException("No posts in the database");
        }

        return allPost.stream()
                .filter(post -> (request.getContent() == null || post.getContent().contains(request.getContent())) &&
                        (request.getAuthor() == null || post.getAuthor().contains(request.getAuthor())) &&
                        (request.getDate() == null || !post.getDateCreated().isBefore(request.getDate())))
                .map(this::mapToPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void sendForReview(ApplyForReviewRequest request, String userRole, String user, String userId) {
        if (!userRole.equals("author")) {
            throw new PostsException("User is not authorized to send a post for review");
        }
        Post post = postRepository.findById(request.getId()).orElseThrow(() -> new PostsException("Post not found"));
        post.setInReview(true);
        post.setInConcept(false);
        postRepository.save(post);

        ApplyForReviewRequestBus requestBus = new ApplyForReviewRequestBus(
                request.getId(),
                request.getAuthorId(),
                request.getTitle(),
                request.getContent(),
                request.getAuthor(),
                request.getDateCreated(),
                request.isInConcept(),
                request.isApproved(),
                request.isInReview(),
                request.getRejectedReason()
        );

        rabbitTemplate.convertAndSend("postQueue1", requestBus);
    }

    @Override
    public void addNotification(AddNotificationRequest request, String userRole) {
        if (!userRole.equals("reviewer")) {
            throw new PostsException("User is not authorized to add a notification");
        }
        Notification notification = new Notification();
        notification.setAuthorId(request.getAuthorId());
        notification.setContent(request.getContent());
        notificationRepository.save(notification);
        System.out.println("Notification added " + notification);
        // Add logging
        System.out.println("AddNotificationRequest: " + request);
        System.out.println("UserRole: " + userRole);
    }

    @Override
    public List<NotificationDTO> getNotifications(String userId, String userRole) {
        if (!userRole.equals("author") && !userRole.equals("user")) {
            throw new PostsException("User is not authorized to get a notification");
        }

        List<Notification> notifications = notificationRepository.findNotificationsByAuthorId(Long.parseLong(userId));
        return notifications.stream()
                .map(this::mapToNotificationDTO)
                .collect(Collectors.toList());

    }

    @RabbitListener(queues = "reviewQueue1")
    public void receiveReview(ReviewRequest requestBus) {

        Post oldPost = postRepository.findById(Long.valueOf(requestBus.postId())).orElseThrow(() -> new PostsException("Post not found"));

        oldPost.setRejectedReason(requestBus.description());

        oldPost.setApproved(requestBus.status() == ReviewStatus.APPROVED);

        if (oldPost.isApproved()) {
            oldPost.setInReview(false);
            oldPost.setInConcept(false);
        } else {
            oldPost.setInReview(false);
            oldPost.setInConcept(true);
        }

        postRepository.save(oldPost);

    }

    private PostDTO mapToPostDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getAuthorId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getDateCreated(),
                post.isInConcept(),
                post.isApproved(),
                post.isInReview(),
                post.getRejectedReason()
        );
    }

    private NotificationDTO mapToNotificationDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setContent(notification.getContent());
        return dto;
    }


}

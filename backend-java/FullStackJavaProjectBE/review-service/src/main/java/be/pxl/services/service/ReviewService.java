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
import be.pxl.services.service.mapper.ApplyForReviewRequestBusMapper;
import be.pxl.services.service.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService{

    private final RabbitTemplate rabbitTemplate;
    private final PostRepository postRepository;
    private final PostClient postClient;

    @Override
    public void postReview(ReviewRequest review, String userRole) {
        if (review.postId() == null) {
            throw new ReviewException("Post id is null");
        }
        if (review.status() == null) {
            throw new ReviewException("Review is null");
        }
        try {
            ReviewStatus.valueOf(review.status().name());
        } catch (IllegalArgumentException e) {
            throw new ReviewException("Invalid review status: " + review.status());
        }


        if (!userRole.equals("editor")) {
            throw new ReviewException("User is not authorized to review a post");
        }

        Post post = postRepository.findById(Long.valueOf(review.postId())).orElseThrow(() -> new ReviewException("Post not found"));
        AddNotificationRequest notification = new AddNotificationRequest();
        long userId = post.getAuthorId();
        notification.setAuthorId(userId);
        notification.setContent("Your post has been reviewed");

        postClient.addNotification(notification, "reviewer");

        String joepie = "joepie";

        rabbitTemplate.convertAndSend("reviewQueue1", review);
    }

    @Override
    public List<PostDTO> getReviewsInWait(String userRole, String user, String userId) {
        if (!userRole.equals("editor")) {
            throw new ReviewException("User is not authorized to get reviews in wait");
        }
        List<Post> posts = postRepository.findPostByisApproved(false);
        if (posts.isEmpty()) {
            throw new ReviewException("No reviews in wait");
        }

        return posts.stream()
                .map(PostMapper::mapToPostDTO)
                .collect(Collectors.toList());

    }



    @RabbitListener(queues = "postQueue1")
    public void listen(ApplyForReviewRequestBus requestBus) {
        System.out.println("Message read from postQueue1: " + requestBus);
        if (requestBus == null) {
            throw new ReviewException("Request is null");
        }
        Optional<Post> previousPost = postRepository.findById(requestBus.id());
        //delete if present in repository
        previousPost.ifPresent(postRepository::delete);

        Post post = ApplyForReviewRequestBusMapper.mapToPost(requestBus);
        postRepository.save(post);

    }
}

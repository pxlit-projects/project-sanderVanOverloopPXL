package be.pxl.services.controller;

import be.pxl.services.controller.Requests.*;
import be.pxl.services.controller.dto.NotificationDTO;
import be.pxl.services.controller.dto.PostDTO;
import be.pxl.services.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController  {

    private final PostService postService;
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @PostMapping
    public ResponseEntity<Void> addPost(@Valid @RequestBody PostRequest request, @RequestHeader("Role") String userRole, @RequestHeader("User") String username, @RequestHeader("Userid") String userId) {
        postService.addPost(request, userRole, username,userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/inConcept")
    public ResponseEntity<List<PostDTO>> getPostsInConcept(@RequestHeader("User") String user, @RequestHeader("Role") String userRole,@RequestHeader("Userid") String userId) {

        return new ResponseEntity<>(postService.getPostsInConcept(user, userRole, userId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable long id, @Valid @RequestBody EditPostRequest request, @RequestHeader("Role") String userRole, @RequestHeader("User") String user,@RequestHeader("Userid") String userId) {
        postService.updatePost(id, request, userRole, user, userId);
        logger.info("Post has been updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @GetMapping("/allpublic")
    public ResponseEntity<List<PostDTO>> getAllPublicPosts() {
        return new ResponseEntity<>(postService.getAllPublicPosts(), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PostDTO>> filterPosts(@RequestBody FilterPostsRequest request) {
        return new ResponseEntity<>(postService.filterPosts(request), HttpStatus.OK);
    }

    @PostMapping("/review")
    public ResponseEntity<PostDTO> sendForReview(@RequestBody ApplyForReviewRequest request, @RequestHeader("Role") String userRole, @RequestHeader("User") String user, @RequestHeader("Userid") String userId) {
        postService.sendForReview(request, userRole, user, userId);
        logger.info("Post has been sent for review");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add/notification")
    public ResponseEntity<Void> addNotification(@RequestBody AddNotificationRequest request, @RequestHeader("Role") String userRole) {
        postService.addNotification(request, userRole);
        logger.info("notification has been added");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDTO>> getNotifications(@RequestHeader("Userid") String userId, @RequestHeader("Role") String userRole) {
        return new ResponseEntity<List<NotificationDTO>>(postService.getNotifications(userId, userRole), HttpStatus.OK);
    }

}

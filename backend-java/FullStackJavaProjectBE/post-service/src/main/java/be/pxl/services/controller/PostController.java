package be.pxl.services.controller;

import be.pxl.services.controller.Requests.ApplyForReviewRequest;
import be.pxl.services.controller.Requests.EditPostRequest;
import be.pxl.services.controller.Requests.FilterPostsRequest;
import be.pxl.services.controller.Requests.PostRequest;
import be.pxl.services.controller.dto.PostDTO;
import be.pxl.services.domain.Post;
import be.pxl.services.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController  {

    private final PostService postService;

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
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

package be.pxl.services.controller;

import be.pxl.services.controller.Requests.EditPostRequest;
import be.pxl.services.controller.Requests.PostRequest;
import be.pxl.services.controller.dto.PostDTO;
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
    public ResponseEntity<Void> addPost(@Valid @RequestBody PostRequest request, @RequestHeader("Role") String userRole, @RequestHeader("User") String username) {
        postService.addPost(request, userRole, username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/inConcept")
    public ResponseEntity<List<PostDTO>> getPostsInConcept(@RequestHeader("User") String user, @RequestHeader("Role") String userRole) {
        return new ResponseEntity<>(postService.getPostsInConcept(user, userRole), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable long id, @Valid @RequestBody EditPostRequest request, @RequestHeader("Role") String userRole, @RequestHeader("User") String user) {
        postService.updatePost(id, request, userRole, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

}

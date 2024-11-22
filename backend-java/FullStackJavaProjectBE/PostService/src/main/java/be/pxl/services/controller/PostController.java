package be.pxl.services.controller;

import be.pxl.services.controller.Requests.PostRequest;
import be.pxl.services.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController  {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> addPost(@Valid @RequestBody PostRequest request) {
        postService.addPost(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

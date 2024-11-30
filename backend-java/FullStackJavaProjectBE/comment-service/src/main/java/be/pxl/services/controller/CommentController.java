package be.pxl.services.controller;

import be.pxl.services.controller.request.AddCommentRequest;
import be.pxl.services.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> addComment(@Valid @RequestBody AddCommentRequest request, @RequestHeader("Role") String userRole, @RequestHeader("User") String username, @RequestHeader("Userid") String userId) {
        commentService.addComment(request, userRole, username, userId);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}

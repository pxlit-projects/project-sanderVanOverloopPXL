package be.pxl.services.controller;

import be.pxl.services.controller.dto.CommentDTO;
import be.pxl.services.controller.request.AddCommentRequest;
import be.pxl.services.controller.request.EditCommentRequest;
import be.pxl.services.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable long postId) {

        return new ResponseEntity<List<CommentDTO>>(commentService.getComments(postId),HttpStatus.OK);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<Void> editComment(@PathVariable long commentId, @RequestBody EditCommentRequest request, @RequestHeader("Role") String userRole, @RequestHeader("User") String username, @RequestHeader("Userid") String userId) {
        commentService.editComment(commentId, request, userRole, username, userId);
        return new ResponseEntity<Void>( HttpStatus.OK);


    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable long commentId, @RequestHeader("Role") String userRole, @RequestHeader("User") String username, @RequestHeader("Userid") String userId) {
        commentService.deleteComment(commentId, userRole, username, userId);
        return new ResponseEntity<Void>( HttpStatus.OK);


    }


}

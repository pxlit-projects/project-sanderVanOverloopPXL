package be.pxl.services.service;

import be.pxl.services.controller.request.AddCommentRequest;
import be.pxl.services.domain.Comment;
import be.pxl.services.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService{

    private final CommentRepository commentRepository;



    @Override
    public void addComment(AddCommentRequest request, String userRole, String username, String userId) {

        if (!userRole.equals("user") && !userRole.equals("author")) {
            throw new RuntimeException("User is not authorized to add a comment");
        }




        Comment comment = Comment.builder()
                .message(request.getMessage())
                .postId(request.getPostId())
                .userId(Long.parseLong(userId))
                .usernameMadeBy(username)
                .dateCreated(LocalDate.now())
                .build();





        commentRepository.save(comment);
    }
}

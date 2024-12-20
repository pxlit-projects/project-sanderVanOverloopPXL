package be.pxl.services.service;

import be.pxl.services.controller.dto.CommentDTO;
import be.pxl.services.controller.request.AddCommentRequest;
import be.pxl.services.controller.request.EditCommentRequest;
import be.pxl.services.domain.Comment;
import be.pxl.services.exceptions.CommentException;
import be.pxl.services.exceptions.UnautherizedException;
import be.pxl.services.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService{

    private final CommentRepository commentRepository;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);


    @Override
    public void addComment(AddCommentRequest request, String userRole, String username, String userId) {

        if (!userRole.equals("user") && !userRole.equals("author") && !userRole.equals("editor")) {
            throw new CommentException("User is not authorized to add a comment");
        }




        Comment comment = Comment.builder()
                .message(request.getMessage())
                .postId(request.getPostId())
                .userId(Long.parseLong(userId))
                .usernameMadeBy(username)
                .dateCreated(LocalDate.now())
                .build();





        commentRepository.save(comment);
        logger.info("Comment has been added");
    }

    @Override
    public List<CommentDTO> getComments(long postId) {
        List<Comment> comments = commentRepository.findAll().stream().filter(comment -> comment.getPostId() == postId).toList();
        return comments.stream()
                .map(comment -> new CommentDTO(comment.getId(),comment.getMessage(), comment.getUsernameMadeBy(), comment.getDateCreated()))
                .toList();
    }

    @Override
    public CommentDTO editComment(long commentId, EditCommentRequest request, String userRole, String username, String userId) {
        if (!userRole.equals("user") && !userRole.equals("author")) {
            throw new UnautherizedException("User is not authorized to edit a comment");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("Comment not found"));

        if (!(comment.getUserId() == (Long.parseLong(userId)))) {
            throw new UnautherizedException("User is not authorized to edit this comment");
        }
        comment.setMessage(request.getMessage());

        commentRepository.save(comment);
        logger.info("Comment has been edited");


        return new CommentDTO(comment.getId(),comment.getMessage(), comment.getUsernameMadeBy(), comment.getDateCreated());
    }

    @Override
    public void deleteComment(long commentId, String userRole, String username, String userId) {
        if (!userRole.equals("user") && !userRole.equals("author")) {
            throw new UnautherizedException("User is not authorized to delete a comment");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("Comment not found"));

        if (!(comment.getUserId() == (Long.parseLong(userId)))) {
            throw new UnautherizedException("User is not authorized to delete this comment");
        }

        commentRepository.delete(comment);
        logger.info("Comment has been deleted");
    }
}

package be.pxl.services.service;

import be.pxl.services.controller.dto.CommentDTO;
import be.pxl.services.controller.request.AddCommentRequest;
import be.pxl.services.controller.request.EditCommentRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface ICommentService {
    void addComment(@Valid AddCommentRequest request, String userRole, String username, String userId);

    List<CommentDTO> getComments(long postId);

    CommentDTO editComment(long commentId, EditCommentRequest request, String userRole, String username, String userId);

    void deleteComment(long commentId,  String userRole, String username, String userId);
}




package be.pxl.services.service;

import be.pxl.services.controller.request.AddCommentRequest;
import jakarta.validation.Valid;

public interface ICommentService {
    void addComment(@Valid AddCommentRequest request, String userRole, String username, String userId);
}


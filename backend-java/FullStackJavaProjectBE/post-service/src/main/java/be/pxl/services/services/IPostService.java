package be.pxl.services.services;

import be.pxl.services.controller.Requests.*;
import be.pxl.services.controller.dto.PostDTO;

import javax.validation.Valid;
import java.util.List;

public interface IPostService {

    void addPost(PostRequest post, String userRole, String user, String userId);

    List<PostDTO> getPostsInConcept(String user, String userRole, String userId);

    void updatePost(long id, @Valid EditPostRequest request, String userRole, String user, String userId);

    PostDTO getPostById(long id);

    List<PostDTO> getAllPublicPosts();

    List<PostDTO> filterPosts(FilterPostsRequest request);

    void sendForReview(ApplyForReviewRequest request, String userRole, String user, String userId);

}





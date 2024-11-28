package be.pxl.services.services;

import be.pxl.services.controller.Requests.EditPostRequest;
import be.pxl.services.controller.Requests.FilterPostsRequest;
import be.pxl.services.controller.Requests.PostRequest;
import be.pxl.services.controller.dto.PostDTO;

import javax.validation.Valid;
import java.util.List;

public interface IPostService {

    void addPost(PostRequest post, String userRole, String user);

    List<PostDTO> getPostsInConcept(String user, String userRole);

    void updatePost(long id, @Valid EditPostRequest request, String userRole, String user);

    PostDTO getPostById(long id);

    List<PostDTO> getAllPublicPosts();

    List<PostDTO> filterPosts(FilterPostsRequest request);
}





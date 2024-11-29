package be.pxl.services.services;

import be.pxl.services.Exceptions.PostsException;
import be.pxl.services.Exceptions.UnautherizedException;
import be.pxl.services.controller.Requests.EditPostRequest;
import be.pxl.services.controller.Requests.FilterPostsRequest;
import be.pxl.services.controller.Requests.PostRequest;
import be.pxl.services.controller.dto.PostDTO;
import be.pxl.services.domain.Post;
import be.pxl.services.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final PostRepository postRepository;

    @Override
    public void addPost(PostRequest post, String userRole, String user) {
        if (!userRole.equals("editor")) {
            throw new PostsException("User is not authorized to add a post");
        }
        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setAuthor(user);
        newPost.setDateCreated(post.getDateCreated());
        postRepository.save(newPost);
    }

    @Override
    public List<PostDTO> getPostsInConcept(String user, String userRole) {
        List<Post> posts = postRepository.findPostByInConceptAndAuthor(true,user);
        if (posts.isEmpty()) {
            throw new PostsException("No posts in concept");
        }

        return posts.stream()
                .map(post -> new PostDTO(post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor(),
                        post.getDateCreated(),
                        post.isInConcept()))
                .collect(Collectors.toList());
    }

    @Override
    public void updatePost(long id, @Valid EditPostRequest request, String userRole, String user) {
        if (!userRole.equals("editor")) {
            throw new PostsException("User is not authorized to update a post");
        }
        Post post = postRepository.findById(id).orElseThrow(() -> new PostsException("Post not found"));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setInConcept(request.isInConcept());
        postRepository.save(post);
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostsException("Post not found"));
        return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getAuthor(), post.getDateCreated(), post.isInConcept());
    }

    @Override
    public List<PostDTO> getAllPublicPosts() {
        List<Post> posts = postRepository.findPostByIsPublic(true);
        return posts.stream()
                .map(post -> new PostDTO(post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor(),
                        post.getDateCreated(),
                        post.isInConcept()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> filterPosts(FilterPostsRequest request) {
        List<Post> allPost = postRepository.findAll();
        if (allPost.isEmpty()) {
            throw new PostsException("No posts in the database");
        }

        if (request.getContent() != null && !request.getContent().isEmpty()) {
            return allPost.stream()
                    .filter(post -> post.getContent().contains(request.getContent()))
                    .map(post -> new PostDTO(post.getId(),
                            post.getTitle(),
                            post.getContent(),
                            post.getAuthor(),
                            post.getDateCreated(),
                            post.isInConcept()))
                    .collect(Collectors.toList());
        }

        if (request.getAuthor() != null && !request.getAuthor().isEmpty()){
            return allPost.stream()
                    .filter(post -> post.getAuthor().contains(request.getAuthor()))
                    .map(post -> new PostDTO(post.getId(),
                            post.getTitle(),
                            post.getContent(),
                            post.getAuthor(),
                            post.getDateCreated(),
                            post.isInConcept()))
                    .collect(Collectors.toList());
        }

        if (request.getDate() != null){
            return allPost.stream()
                    .filter(post -> !post.getDateCreated().isBefore(request.getDate()))
                    .map(post -> new PostDTO(post.getId(),
                            post.getTitle(),
                            post.getContent(),
                            post.getAuthor(),
                            post.getDateCreated(),
                            post.isInConcept()))
                    .collect(Collectors.toList());
        }

        return allPost.stream()
                .map(post -> new PostDTO(post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor(),
                        post.getDateCreated(),
                        post.isInConcept()))
                .collect(Collectors.toList());
    }


}

package be.pxl.services.services;

import be.pxl.services.controller.Requests.PostRequest;
import be.pxl.services.domain.Post;
import be.pxl.services.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final PostRepository postRepository;

    @Override
    public void addPost(PostRequest post) {
        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setAuthor(post.getAuthor());
        newPost.setDateCreated(post.getDateCreated());
        postRepository.save(newPost);
    }
}

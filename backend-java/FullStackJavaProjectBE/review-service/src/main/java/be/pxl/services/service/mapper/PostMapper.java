package be.pxl.services.service.mapper;

import be.pxl.services.controllers.dto.PostDTO;
import be.pxl.services.domain.Post;

public class PostMapper {
    public static PostDTO mapToPostDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getAuthorId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getDateCreated(),
                post.isInConcept(),
                post.isApproved(),
                post.isInReview(),
                post.getRejectedReason()
        );
    }
}

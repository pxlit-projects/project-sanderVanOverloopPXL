package be.pxl.services.repository;

import be.pxl.services.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostByInConceptAndAuthorId(boolean inConcept, long authorId);

    List<Post> findPostByInConcept(boolean inConcept);

    List<Post> findPostByisApproved(boolean isApproved);
}

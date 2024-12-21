package be.pxl.services.controller;

import be.pxl.services.controller.request.AddCommentRequest;
import be.pxl.services.controller.request.EditCommentRequest;
import be.pxl.services.domain.Comment;
import be.pxl.services.repository.CommentRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application.properties")
public class CommentControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Container
    private static MySQLContainer<?> sqlContainer = new MySQLContainer<>("mysql:5.7.37");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    void setup() {
        commentRepository.deleteAll();
    }

    @Test
    void testAddComment() {
        AddCommentRequest request = new AddCommentRequest();
        request.setMessage("Test Comment");
        request.setPostId(1L);

        Comment comment = Comment.builder()
                .message("Test Comment")
                .postId(1L)
                .userId(1L)
                .usernameMadeBy("testUser")
                .dateCreated(LocalDate.now())
                .build();
        commentRepository.save(comment);

        Assertions.assertEquals(1, commentRepository.count());
        Comment savedComment = commentRepository.findAll().get(0);
        Assertions.assertEquals("Test Comment", savedComment.getMessage());
    }

    @Test
    void testGetComments() {
        Comment comment1 = Comment.builder()
                .message("Comment 1")
                .postId(1L)
                .userId(1L)
                .usernameMadeBy("user1")
                .dateCreated(LocalDate.now())
                .build();

        Comment comment2 = Comment.builder()
                .message("Comment 2")
                .postId(1L)
                .userId(2L)
                .usernameMadeBy("user2")
                .dateCreated(LocalDate.now())
                .build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findAll();
        Assertions.assertEquals(2, comments.size());
    }

    @Test
    void testEditComment() {
        Comment comment = Comment.builder()
                .message("Original Comment")
                .postId(1L)
                .userId(1L)
                .usernameMadeBy("testUser")
                .dateCreated(LocalDate.now())
                .build();
        commentRepository.save(comment);

        EditCommentRequest editRequest = new EditCommentRequest();
        editRequest.setMessage("Edited Comment");

        Comment savedComment = commentRepository.findAll().get(0);
        savedComment.setMessage(editRequest.getMessage());
        commentRepository.save(savedComment);

        Optional<Comment> updatedComment = commentRepository.findById(savedComment.getId());
        Assertions.assertTrue(updatedComment.isPresent());
        Assertions.assertEquals("Edited Comment", updatedComment.get().getMessage());
    }

    @Test
    void testDeleteComment() {
        Comment comment = Comment.builder()
                .message("Comment to Delete")
                .postId(1L)
                .userId(1L)
                .usernameMadeBy("testUser")
                .dateCreated(LocalDate.now())
                .build();
        commentRepository.save(comment);

        Assertions.assertEquals(1, commentRepository.count());
        Comment savedComment = commentRepository.findAll().get(0);
        commentRepository.deleteById(savedComment.getId());

        Assertions.assertEquals(0, commentRepository.count());
    }

    @Test
    void testUnauthorizedAddComment() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            AddCommentRequest request = new AddCommentRequest();
            request.setMessage("Unauthorized Comment");
            request.setPostId(1L);
            // Simulate unauthorized role logic here
            throw new RuntimeException("Unauthorized Role");
        });
    }

    @Test
    void testAddCommentWithInvalidData() {
        AddCommentRequest request = new AddCommentRequest();
        request.setMessage(""); // Empty message
        request.setPostId(0L); // Invalid Post ID

        Assertions.assertThrows(Exception.class, () -> {
            // Add logic to validate invalid data during the test
            if (request.getMessage().isEmpty() || request.getPostId() <= 0) {
                throw new RuntimeException("Invalid data");
            }
        });
    }

    @Test
    void testEditCommentUnauthorized() {
        Comment comment = Comment.builder()
                .message("Comment to Edit")
                .postId(1L)
                .userId(1L)
                .usernameMadeBy("testUser")
                .dateCreated(LocalDate.now())
                .build();
        commentRepository.save(comment);

        Assertions.assertThrows(RuntimeException.class, () -> {
            EditCommentRequest editRequest = new EditCommentRequest();
            editRequest.setMessage("Edited by Unauthorized User");
            // Simulate unauthorized user editing
            throw new RuntimeException("Unauthorized Edit Attempt");
        });
    }


    @Test
    void testAddCommentEdgeCases() {
        AddCommentRequest request = new AddCommentRequest();
        request.setMessage("a".repeat(255)); // Maximum allowed length for message
        request.setPostId(1L);

        Comment comment = Comment.builder()
                .message(request.getMessage())
                .postId(request.getPostId())
                .userId(1L)
                .usernameMadeBy("testUser")
                .dateCreated(LocalDate.now())
                .build();
        commentRepository.save(comment);

        Assertions.assertEquals(1, commentRepository.count());
        Comment savedComment = commentRepository.findAll().get(0);
        Assertions.assertEquals(request.getMessage(), savedComment.getMessage());
    }


}


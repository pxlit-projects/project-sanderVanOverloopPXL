package be.pxl.services.controller;


import be.pxl.services.controller.dto.CommentDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
    void testAddComment() throws Exception {
        AddCommentRequest request = new AddCommentRequest("Test message", 1L);

        mockMvc.perform(post("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Role", "user")
                        .header("User", "testUser")
                        .header("Userid", "1"))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetComments() throws Exception {
        Comment comment = new Comment(1L, "Test message", 1L, "testUser", 1L, LocalDate.now());
        commentRepository.save(comment);

        mockMvc.perform(get("/api/comment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Test message"));
    }

    @Test
    void testEditComment() throws Exception {
        Comment comment = new Comment(1L, "Test message", 1L, "testUser", 1L, LocalDate.now());
        commentRepository.save(comment);

        EditCommentRequest request = new EditCommentRequest("Updated message");

        mockMvc.perform(post("/api/comment/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Role", "user")
                        .header("User", "testUser")
                        .header("Userid", "1"))
                .andExpect(status().isOk());

        Comment updatedComment = commentRepository.findById(1L).orElseThrow();
        Assertions.assertEquals("Updated message", updatedComment.getMessage());
    }




}


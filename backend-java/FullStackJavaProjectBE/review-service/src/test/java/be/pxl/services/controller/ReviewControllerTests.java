package be.pxl.services.controller;

import be.pxl.services.Client.PostClient;
import be.pxl.services.controllers.dto.PostDTO;
import be.pxl.services.controllers.requests.ReviewRequest;
import be.pxl.services.domain.Post;
import be.pxl.services.domain.ReviewStatus;
import be.pxl.services.repository.PostRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application.properties")
public class ReviewControllerTests {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @MockBean
    private PostClient postClient;

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
        postRepository.deleteAll();
    }

    @Test
    void testPostReview() throws Exception {
        // Save the post before testing the review
        Post post = Post.builder()
                .id(1L)
                .authorId(1L)
                .title("Test post")
                .content("Test content")
                .dateCreated(LocalDateTime.now())
                .isApproved(false)
                .build();
        postRepository.save(post);

        ReviewRequest request = new ReviewRequest("1", ReviewStatus.APPROVED, "Test review");

        // Mock the PostClient response
        Mockito.when(postClient.addNotification(Mockito.any(), Mockito.anyString()))
                .thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/api/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Role", "editor"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetReviewsInWait() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .authorId(1L)
                .title("Test post")
                .content("Test content")
                .dateCreated(LocalDateTime.now())
                .isApproved(false)
                .build();
        postRepository.save(post);

        mockMvc.perform(get("/api/review")
                        .header("Role", "editor")
                        .header("User", "testUser")
                        .header("Userid", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test post"));
    }
}
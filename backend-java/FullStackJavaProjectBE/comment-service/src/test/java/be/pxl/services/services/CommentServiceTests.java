package be.pxl.services.services;

import be.pxl.services.controller.dto.CommentDTO;
import be.pxl.services.controller.request.AddCommentRequest;
import be.pxl.services.controller.request.EditCommentRequest;
import be.pxl.services.domain.Comment;
import be.pxl.services.exceptions.CommentException;
import be.pxl.services.exceptions.UnautherizedException;
import be.pxl.services.repository.CommentRepository;
import be.pxl.services.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CommentServiceTests {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddComment() {
        AddCommentRequest request = new AddCommentRequest("Test message", 1L);
        Comment comment = new Comment(1L, "Test message", 1L, "testUser", 1L, LocalDate.now());

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        commentService.addComment(request, "user", "testUser", "1");

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testAddCommentUnauthorized() {
        AddCommentRequest request = new AddCommentRequest("Test message", 1L);

        assertThrows(CommentException.class, () -> {
            commentService.addComment(request, "guest", "testUser", "1");
        });
    }

    @Test
    void testAddCommentInvalidRole() {
        AddCommentRequest request = new AddCommentRequest("Test message", 1L);

        assertThrows(CommentException.class, () -> {
            commentService.addComment(request, "invalidRole", "testUser", "1");
        });
    }

    @Test
    void testGetComments() {
        Comment comment = new Comment(1L, "Test message", 1L, "testUser", 1L, LocalDate.now());
        when(commentRepository.findAll()).thenReturn(Collections.singletonList(comment));

        List<CommentDTO> comments = commentService.getComments(1L);

        assertEquals(1, comments.size());
        assertEquals("Test message", comments.get(0).getMessage());
    }

    @Test
    void testEditComment() {
        EditCommentRequest request = new EditCommentRequest("Updated message");
        Comment comment = new Comment(1L, "Test message", 1L, "testUser", 1L, LocalDate.now());

        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDTO updatedComment = commentService.editComment(1L, request, "user", "testUser", "1");

        assertEquals("Updated message", updatedComment.getMessage());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testEditCommentUnauthorized() {
        EditCommentRequest request = new EditCommentRequest("Updated message");
        Comment comment = new Comment(1L, "Test message", 1L, "testUser", 1L, LocalDate.now());

        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        assertThrows(UnautherizedException.class, () -> {
            commentService.editComment(1L, request, "guest", "testUser", "1");
        });
    }

    @Test
    void testEditCommentNotFound() {
        EditCommentRequest request = new EditCommentRequest("Updated message");

        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CommentException.class, () -> {
            commentService.editComment(1L, request, "user", "testUser", "1");
        });
    }

    @Test
    void testDeleteComment() {
        Comment comment = new Comment(1L, "Test message", 1L, "testUser", 1L, LocalDate.now());

        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        commentService.deleteComment(1L, "user", "testUser", "1");

        verify(commentRepository, times(1)).delete(any(Comment.class));
    }

    @Test
    void testDeleteCommentUnauthorized() {
        Comment comment = new Comment(1L, "Test message", 1L, "testUser", 1L, LocalDate.now());

        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        assertThrows(UnautherizedException.class, () -> {
            commentService.deleteComment(1L, "guest", "testUser", "1");
        });
    }

    @Test
    void testDeleteCommentNotFound() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CommentException.class, () -> {
            commentService.deleteComment(1L, "user", "testUser", "1");
        });
    }
}

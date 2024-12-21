package be.pxl.services.domain;

import be.pxl.services.controller.request.AddCommentRequest;
import be.pxl.services.controller.request.EditCommentRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RequestTests {
    @Test
    void testAddCommentRequestAllArgsConstructor() {
        AddCommentRequest request = new AddCommentRequest("Test message", 1L);

        assertNotNull(request);
        assertEquals("Test message", request.getMessage());
        assertEquals(1L, request.getPostId());
    }

    @Test
    void testAddCommentRequestNoArgsConstructor() {
        AddCommentRequest request = new AddCommentRequest();

        assertNotNull(request);
    }

    @Test
    void testAddCommentRequestSettersAndGetters() {
        AddCommentRequest request = new AddCommentRequest();
        request.setMessage("Test message");
        request.setPostId(1L);

        assertEquals("Test message", request.getMessage());
        assertEquals(1L, request.getPostId());
    }

    @Test
    void testEditCommentRequestAllArgsConstructor() {
        EditCommentRequest request = new EditCommentRequest("Updated message");

        assertNotNull(request);
        assertEquals("Updated message", request.getMessage());
    }

    @Test
    void testEditCommentRequestNoArgsConstructor() {
        EditCommentRequest request = new EditCommentRequest();

        assertNotNull(request);
    }

    @Test
    void testEditCommentRequestSettersAndGetters() {
        EditCommentRequest request = new EditCommentRequest();
        request.setMessage("Updated message");

        assertEquals("Updated message", request.getMessage());
    }
}

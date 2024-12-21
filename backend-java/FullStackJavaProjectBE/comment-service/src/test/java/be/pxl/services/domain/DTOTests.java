package be.pxl.services.domain;


import be.pxl.services.controller.dto.CommentDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DTOTests {

    @Test
    void testCommentDTOAllArgsConstructor() {
        LocalDate dateCreated = LocalDate.now();
        CommentDTO commentDTO = new CommentDTO(1L, "Test message", "testUser", dateCreated);

        assertNotNull(commentDTO);
        assertEquals(1L, commentDTO.getId());
        assertEquals("Test message", commentDTO.getMessage());
        assertEquals("testUser", commentDTO.getUsernameMadeBy());
        assertEquals(dateCreated, commentDTO.getDateCreated());
    }

    @Test
    void testCommentDTONoArgsConstructor() {
        CommentDTO commentDTO = new CommentDTO();

        assertNotNull(commentDTO);
    }

    @Test
    void testCommentDTOSettersAndGetters() {
        LocalDate dateCreated = LocalDate.now();
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setMessage("Test message");
        commentDTO.setUsernameMadeBy("testUser");
        commentDTO.setDateCreated(dateCreated);

        assertEquals(1L, commentDTO.getId());
        assertEquals("Test message", commentDTO.getMessage());
        assertEquals("testUser", commentDTO.getUsernameMadeBy());
        assertEquals(dateCreated, commentDTO.getDateCreated());
    }
}

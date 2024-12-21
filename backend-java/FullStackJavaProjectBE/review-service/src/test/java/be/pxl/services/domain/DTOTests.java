package be.pxl.services.domain;

import be.pxl.services.controllers.dto.PostDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DTOTests {
    @Test
    public void testPostDTO() {
        LocalDateTime now = LocalDateTime.now();
        PostDTO postDTO = new PostDTO(1L, 2L, "Title", "Content", "Author", now, true, false, true, "Rejected Reason");

        assertEquals(1L, postDTO.getId());
        assertEquals(2L, postDTO.getAuthorId());
        assertEquals("Title", postDTO.getTitle());
        assertEquals("Content", postDTO.getContent());
        assertEquals("Author", postDTO.getAuthor());
        assertEquals(now, postDTO.getDateCreated());
        assertTrue(postDTO.isInConcept());
        assertTrue(postDTO.isInReview());
        assertEquals("Rejected Reason", postDTO.getRejectedReason());
        assertEquals(false, postDTO.isApproved());
    }
}

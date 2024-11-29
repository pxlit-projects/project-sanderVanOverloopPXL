package be.pxl.services.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data //  provides getters, setters, and other utility methods
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private long id;
    private long authorId;
    private String title;
    private String content;
    private String author;
    private LocalDate dateCreated;
    private boolean inConcept;
    private boolean isApproved = false;
    private boolean inReview;
    private String rejectedReason = "";
}

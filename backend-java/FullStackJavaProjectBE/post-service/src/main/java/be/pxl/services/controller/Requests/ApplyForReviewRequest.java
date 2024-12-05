package be.pxl.services.controller.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data //  provides getters, setters, and other utility methods
@AllArgsConstructor
@NoArgsConstructor
public class ApplyForReviewRequest {
    private long id;
    private long authorId;
    private String title;
    private String content;
    private String author;
    private LocalDateTime dateCreated;
    private boolean inConcept;
    private boolean isApproved = false;
    private boolean inReview;
    private String rejectedReason = "";
}

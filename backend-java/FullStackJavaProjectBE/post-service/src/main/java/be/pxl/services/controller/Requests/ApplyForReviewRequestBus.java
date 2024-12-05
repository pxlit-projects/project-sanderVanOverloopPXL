package be.pxl.services.controller.Requests;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


public record ApplyForReviewRequestBus(
        long id,
        long authorId,
        String title,
        String content,
        String author,
        LocalDateTime dateCreated,
        boolean inConcept,
        boolean isApproved,
        boolean inReview,
        String rejectedReason
) implements Serializable {
}



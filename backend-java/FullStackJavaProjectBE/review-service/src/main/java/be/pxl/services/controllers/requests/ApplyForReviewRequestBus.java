package be.pxl.services.controllers.requests;

import java.io.Serializable;
import java.time.LocalDate;


public record ApplyForReviewRequestBus(
        long id,
        long authorId,
        String title,
        String content,
        String author,
        LocalDate dateCreated,
        boolean inConcept,
        boolean isApproved,
        boolean inReview,
        String rejectedReason
) implements Serializable {
}



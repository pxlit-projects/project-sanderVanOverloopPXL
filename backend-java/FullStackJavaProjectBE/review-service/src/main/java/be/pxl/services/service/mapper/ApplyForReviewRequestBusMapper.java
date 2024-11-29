package be.pxl.services.service.mapper;

import be.pxl.services.controllers.requests.ApplyForReviewRequestBus;
import be.pxl.services.domain.Post;

public class ApplyForReviewRequestBusMapper {
    public static Post mapToPost(ApplyForReviewRequestBus requestBus) {
        return Post.builder()
                .id(requestBus.id())
                .authorId(requestBus.authorId())
                .title(requestBus.title())
                .content(requestBus.content())
                .author(requestBus.author())
                .dateCreated(requestBus.dateCreated())
                .inConcept(requestBus.inConcept())
                .isApproved(requestBus.isApproved())
                .inReview(requestBus.inReview())
                .rejectedReason(requestBus.rejectedReason())
                .build();
    }
}

package be.pxl.services.service;

import be.pxl.services.controllers.requests.ReviewRequest;

public interface IReviewService {

    void postReview(ReviewRequest review);
}

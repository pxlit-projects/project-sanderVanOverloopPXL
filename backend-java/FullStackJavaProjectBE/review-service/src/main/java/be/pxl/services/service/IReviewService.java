package be.pxl.services.service;

import be.pxl.services.controllers.dto.PostDTO;
import be.pxl.services.controllers.requests.ReviewRequest;

import java.util.List;

public interface IReviewService {

    void postReview(ReviewRequest review, String userRole);


    List<PostDTO> getReviewsInWait(String userRole, String user, String userId);


}


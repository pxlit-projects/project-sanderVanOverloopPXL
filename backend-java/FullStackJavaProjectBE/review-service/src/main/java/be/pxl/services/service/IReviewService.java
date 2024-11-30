package be.pxl.services.service;

import be.pxl.services.controllers.dto.NotificationDTO;
import be.pxl.services.controllers.dto.PostDTO;
import be.pxl.services.controllers.requests.ReviewRequest;

import java.util.List;

public interface IReviewService {

    void postReview(ReviewRequest review);


    List<PostDTO> getReviewsInWait(String userRole, String user, String userId);

    List<NotificationDTO> getNotification(String userRole, String user, String userId);
}


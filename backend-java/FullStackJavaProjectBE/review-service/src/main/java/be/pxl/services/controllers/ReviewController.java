package be.pxl.services.controllers;

import be.pxl.services.controllers.dto.NotificationDTO;
import be.pxl.services.controllers.dto.PostDTO;
import be.pxl.services.domain.Notification;
import be.pxl.services.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getReviewsInWait(@RequestHeader("Role") String userRole, @RequestHeader("User") String user, @RequestHeader("Userid") String userId) {
        return new ResponseEntity<List<PostDTO>>(reviewService.getReviewsInWait(userRole,user,userId), HttpStatus.OK);
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<List<NotificationDTO>> getNotification(@RequestHeader("Role") String userRole, @RequestHeader("User") String user, @RequestHeader("Userid") String userId) {
        return new ResponseEntity<List<NotificationDTO>>(reviewService.getNotification(userRole,user,userId), HttpStatus.OK);
    }
}

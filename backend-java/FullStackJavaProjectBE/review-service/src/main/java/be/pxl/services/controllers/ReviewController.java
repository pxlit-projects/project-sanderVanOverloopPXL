package be.pxl.services.controllers;

import be.pxl.services.controllers.dto.PostDTO;
import be.pxl.services.controllers.requests.ReviewRequest;
import be.pxl.services.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Void> postReview(@RequestBody ReviewRequest review, @RequestHeader("Role") String userRole) {
        reviewService.postReview(review,userRole);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

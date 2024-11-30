package be.pxl.services.client;

import be.pxl.services.controller.Requests.AddCommentRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "comment-service")
public interface CommentClient {
    @PostMapping
    public ResponseEntity<Void> addComment(@Valid @RequestBody AddCommentRequest request, @RequestHeader("Role") String userRole, @RequestHeader("User") String username, @RequestHeader("Userid") String userId);
}

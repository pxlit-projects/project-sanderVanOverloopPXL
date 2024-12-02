package be.pxl.services.Client;

import be.pxl.services.controllers.requests.AddNotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "post-service")
public interface PostClient {
    @PostMapping("/api/post/add/notification")
    ResponseEntity<Void> addNotification(@RequestBody AddNotificationRequest request, @RequestHeader("Role") String userRole);

}

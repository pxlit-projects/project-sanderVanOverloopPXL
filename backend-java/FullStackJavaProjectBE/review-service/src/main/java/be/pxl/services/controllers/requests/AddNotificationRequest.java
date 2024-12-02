package be.pxl.services.controllers.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //  provides getters, setters, and other utility methods
@AllArgsConstructor
@NoArgsConstructor
public class AddNotificationRequest {
    private long authorId;
    private String content;
}

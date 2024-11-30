package be.pxl.services.controller.Requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //  provides getters, setters, and other utility methods
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentRequest {
    @NotBlank
    private String message;
    @NotBlank
    private long postId;

}

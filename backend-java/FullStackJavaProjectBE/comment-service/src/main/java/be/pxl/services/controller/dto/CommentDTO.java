package be.pxl.services.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data //  provides getters, setters, and other utility methods
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String message;
    private String usernameMadeBy;
    private LocalDate dateCreated;
}

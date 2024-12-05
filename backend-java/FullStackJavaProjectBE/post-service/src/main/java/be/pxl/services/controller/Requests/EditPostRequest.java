package be.pxl.services.controller.Requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Data //  provides getters, setters, and other utility methods
@AllArgsConstructor
@NoArgsConstructor
public class EditPostRequest {
    @NotBlank(message = "Title is mandatory")
    @NotNull(message = "Title is mandatory")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;
    @NotBlank(message = "Content is mandatory")
    private String content;
    @NotNull(message = "Date created is mandatory")
    private LocalDateTime dateCreated;
    @NotNull
    private boolean inConcept;
}

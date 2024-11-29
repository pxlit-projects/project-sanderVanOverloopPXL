package be.pxl.services.controllers.requests;

import be.pxl.services.domain.ReviewStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


public record ReviewRequest(String postId, ReviewStatus status, String description) implements Serializable {
}


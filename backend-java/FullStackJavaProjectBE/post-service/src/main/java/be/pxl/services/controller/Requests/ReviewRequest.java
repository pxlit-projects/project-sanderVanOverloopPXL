package be.pxl.services.controller.Requests;

import be.pxl.services.domain.ReviewStatus;

import java.io.Serializable;


public record ReviewRequest(String postId, ReviewStatus status, String description) implements Serializable {
}


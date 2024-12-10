package com.wesp.request;

import lombok.Data;

import java.util.List;

@Data
public class CreatedReviewRequest {
    private String reviewText;
    private Double reviewRating;
    private List<String> productImages;
}

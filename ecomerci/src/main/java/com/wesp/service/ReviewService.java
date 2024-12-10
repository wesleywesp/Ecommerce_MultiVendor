package com.wesp.service;

import com.wesp.model.Product;
import com.wesp.model.Review;
import com.wesp.model.User;
import com.wesp.request.CreatedReviewRequest;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewsByProductId(Long productId);
    Review createReview(CreatedReviewRequest req, User user, Product product);
    Review updateReview(Long reviewId, String reviewText, double rating, Long userId);
    void deleteReview(Long reviewId, Long userId);
    Review getReviewsById(Long reviewId);
}

package com.wesp.service.impl;

import com.wesp.infra.exception.ReviewException;
import com.wesp.model.Product;
import com.wesp.model.Review;
import com.wesp.model.User;
import com.wesp.repository.ReviewRepository;
import com.wesp.request.CreatedReviewRequest;
import com.wesp.service.ProductService;
import com.wesp.service.ReviewService;
import com.wesp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review createReview(CreatedReviewRequest req, User user, Product product) {
        Review review = new Review();
        review.setReviewText(req.getReviewText());
        review.setRating(req.getReviewRating());
        review.setProductImages(req.getProductImages());
        review.setUser(user);
        review.setProduct(product);

        product.getReviews().add(review);
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long reviewId, String reviewText, double rating, Long userId) {
        Review review = getReviewsById(reviewId);
        if (!review.getUser().getId().equals(userId)) {
            throw new ReviewException("You can't update this review");
        }
        review.setReviewText(reviewText);
        review.setRating(rating);
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) {
        Review review = getReviewsById(reviewId);
        if (!review.getUser().getId().equals(userId)) {
            throw new ReviewException("You can't delete this review");
        }
        reviewRepository.delete(review);

    }

    @Override
    public Review getReviewsById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewException("Review not found"));
    }
}

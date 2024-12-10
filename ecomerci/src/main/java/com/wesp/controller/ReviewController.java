package com.wesp.controller;

import com.wesp.infra.exception.CustumerException;
import com.wesp.infra.exception.ProductException;
import com.wesp.model.Product;
import com.wesp.model.Review;
import com.wesp.model.User;
import com.wesp.request.CreatedReviewRequest;
import com.wesp.response.ApiResponse;
import com.wesp.service.ProductService;
import com.wesp.service.ReviewService;
import com.wesp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/review")
@RestController
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;
    @GetMapping("/product/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId));
    }

    @PostMapping("/product/{productId}/review")
    public ResponseEntity<Review> createReview(@PathVariable Long productId,
                                               @RequestBody CreatedReviewRequest req,
                                               @RequestHeader("Authorization") String token) throws CustumerException, ProductException {
        User user = userService.findByJwtToken(token);
        Product product = productService.findProductById(productId);
        return ResponseEntity.ok(reviewService.createReview(req, user, product));
    }
    @PutMapping("/review/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,
                                               @RequestBody CreatedReviewRequest req,
                                               @RequestHeader("Authorization") String token) throws CustumerException {
        User user = userService.findByJwtToken(token);
        return ResponseEntity.ok(reviewService.updateReview(reviewId, req.getReviewText(), req.getReviewRating(), user.getId()));
    }
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId,
                                                   @RequestHeader("Authorization") String token) throws CustumerException {
        User user = userService.findByJwtToken(token);
        reviewService.deleteReview(reviewId, user.getId());
        ApiResponse apiResponse = new ApiResponse("Review deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

}

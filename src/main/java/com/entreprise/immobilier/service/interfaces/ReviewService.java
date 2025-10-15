package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.ReviewDTO;
import com.entreprise.immobilier.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> getAllReviews();

    Optional<Review> getReviewById(Long id);

    Review createReview(ReviewDTO dto);

    Review updateReview(Long id, ReviewDTO dto);

    void deleteReview(Long id);

    List<Review> getReviewsByProperty(Long propertyId);

    List<Review> getReviewsByUser(Long userId);
}

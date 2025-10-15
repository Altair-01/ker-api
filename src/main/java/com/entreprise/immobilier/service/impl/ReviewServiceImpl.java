package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.ReviewDTO;
import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.model.Review;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.PropertyRepository;
import com.entreprise.immobilier.repository.ReviewRepository;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.service.interfaces.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review createReview(ReviewDTO dto) {
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Bien immobilier introuvable."));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));

        Review review = Review.builder()
                .property(property)
                .user(user)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long id, ReviewDTO dto) {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avis introuvable."));

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Bien immobilier introuvable."));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));

        existing.setProperty(property);
        existing.setUser(user);
        existing.setRating(dto.getRating());
        existing.setComment(dto.getComment());

        return reviewRepository.save(existing);
    }

    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Avis introuvable.");
        }
        reviewRepository.deleteById(id);
    }

    @Override
    public List<Review> getReviewsByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Bien immobilier introuvable."));
        return reviewRepository.findByProperty(property);
    }

    @Override
    public List<Review> getReviewsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));
        return reviewRepository.findByUser(user);
    }
}

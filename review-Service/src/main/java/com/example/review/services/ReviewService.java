package com.example.review.services;

import com.example.review.entities.Review;
import com.example.review.payload.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(ReviewDto reviewdto);
    Review findById(String reviewId);
    List<ReviewDto> findAllReviews();
    List<ReviewDto> findAllByHotelId(String hotelId);
    List<ReviewDto> findAllByUserId(String userId);
}

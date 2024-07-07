package com.example.review.services.impl;

import com.example.review.entities.Review;
import com.example.review.exceptions.ResourceNotFoundException;
import com.example.review.payload.ReviewDto;
import com.example.review.repository.ReviewRepository;
import com.example.review.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ReviewDto createReview(ReviewDto reviewdto) {
        String id = UUID.randomUUID().toString();
        Review review = dtoToReview(reviewdto);
        review.setReviewId(id);
        return reviewToDto(reviewRepository.save(review));
    }

    @Override
    public Review findById(String reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new ResourceNotFoundException("Review with Id"+reviewId+"not found"));
        return review;
    }

    @Override
    public List<ReviewDto> findAllReviews() {
        List<Review>reviews = reviewRepository.findAll();
        return reviews.stream().map(this::reviewToDto).collect(Collectors.toList());

    }

    @Override
    public List<ReviewDto> findAllByHotelId(String hotelId) {
        List<Review>reviews = reviewRepository.findAllByHotelId(hotelId);
        return reviews.stream().map(this::reviewToDto).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> findAllByUserId(String userId) {
        List<Review>reviews = reviewRepository.findAllByUserId(userId);
        return reviews.stream().map(this::reviewToDto).collect(Collectors.toList());
    }

    private Review dtoToReview(ReviewDto reviewDto){
        return this.modelMapper.map(reviewDto,Review.class);
    }
    private ReviewDto reviewToDto(Review review){
        return this.modelMapper.map(review,ReviewDto.class);
    }
}

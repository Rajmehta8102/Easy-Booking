package com.example.review.controllers;

import com.example.review.entities.Review;
import com.example.review.payload.ReviewDto;
import com.example.review.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto>createReview(@Valid @RequestBody ReviewDto reviewdto){
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(reviewdto));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review>findById(@PathVariable String reviewId){
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.findById(reviewId));
    }

    @GetMapping("/")
    public ResponseEntity<List<ReviewDto>>getAllReviews(){
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.findAllReviews());
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<ReviewDto>>getAllReviewsByUserId(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.findAllByUserId(userId));
    }

    @GetMapping("/hotelId/{hotelId}")
    public ResponseEntity<List<ReviewDto>>getAllReviewsByHotelId(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.findAllByHotelId(hotelId));
    }
}

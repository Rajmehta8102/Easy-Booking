package com.example.hotel.controllers;

import com.example.hotel.entities.Hotel;
import com.example.hotel.payload.HotelDto;
import com.example.hotel.payload.Review;
import com.example.hotel.services.HotelService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel>createHotel(@Valid @RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto>findHotelById(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.findById(hotelId));
    }

    @GetMapping("/")
    public ResponseEntity<List<Hotel>>getAllHotels(){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAll());
    }

    @GetMapping("/reviews/{hotelId}")
    @CircuitBreaker(name ="reviewBreaker")
    @Retry(name = "reviewRetry",fallbackMethod = "reviewBreakerFallBack")
    @RateLimiter(name="reviewRateLimiter",fallbackMethod = "reviewBreakerFallBack")
    public ResponseEntity<List<Review>>getAllReviews(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.fetchReviewsForHotel(hotelId));
    }

    private ResponseEntity<List<Review>>reviewBreakerFallBack(String hotelId,Exception ex){
        Review review = new Review();
        review.setReviewId("101");
        review.setUserId("101");
        review.setRatings(0);
        review.setComments("Review Service Not Working");
        List<Review>reviews = new ArrayList<>();
        reviews.add(review);
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }
}
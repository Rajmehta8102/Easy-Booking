package com.example.review.repository;

import com.example.review.entities.Review;
import com.example.review.payload.ReviewDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,String> {
    List<Review> findAllByUserId(String userId);
    List<Review> findAllByHotelId(String hotelId);
}

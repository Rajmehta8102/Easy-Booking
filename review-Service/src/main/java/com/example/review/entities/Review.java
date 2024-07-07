package com.example.review.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Review {
    @Id
    private String reviewId;
    @NotEmpty(message = "Enter Valid User Id")
    private String userId;
    @NotEmpty(message = "Enter Valid Hotel Id")
    private String hotelId;
    @NotNull(message = "Rating must be provided")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be less than or equal to 5")
    private Integer ratings;
    private String comments;
}

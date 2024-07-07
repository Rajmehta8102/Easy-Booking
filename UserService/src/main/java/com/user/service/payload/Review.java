package com.user.service.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private String reviewId;
    private String userId;
    private String hotelId;
    private Integer ratings;
    private String comments;
}

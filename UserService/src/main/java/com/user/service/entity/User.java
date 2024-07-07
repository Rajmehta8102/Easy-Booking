package com.user.service.entity;


import com.user.service.payload.Booking;
import com.user.service.payload.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String userId;
    @Column(length = 20)
    private String name;
    @Email(message = "Enter Valid Email Id")
    private String email;
    @Pattern(regexp = "\\d{10}") @Column(name = "mobNumber")
    private String mobileNumber;
    @NotNull
    private String password;
    @Transient
    private List<Review> reviews;
    @Transient
    private List<Booking>bookings;
}

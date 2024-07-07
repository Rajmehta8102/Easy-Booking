package com.user.service.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String userId;
    @NotNull
    private String name;
    @Email(message = "Email not valid")
    private String email;
    @Pattern(regexp = "\\d{10}",message = "Enter Valid Password")
    private String mobileNumber;
    @NotEmpty @Size(min = 6,message = "Password should be more than 6 characters")
    @Pattern(regexp = "^.*(?=.{6,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",message = "Password should be in proper format  ")
    private String password;
    private List<Review> reviews = new ArrayList<>();
    List<RoomBookingReferenceDto>bookings = new ArrayList<>();
}

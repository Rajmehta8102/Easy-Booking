package com.example.Booking.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  BookingRequest {
    @NotEmpty(message = "Please Enter correct hotelId")
    private String hotelId;
    @NotEmpty(message = "Please Enter valid userId")
    private String userId;
    @NotEmpty(message = "Please Enter valid start date")
    private String startDate;
    @NotEmpty(message = "Please Enter valid end date")
    private String endDate;
    private List<RoomNumber>basic = new ArrayList<>();
    private List<RoomNumber>premium = new ArrayList<>();
    private List<RoomNumber>deluxe  = new ArrayList<>();
    private int price;
}


package com.example.Booking.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class BookedRoomsRequest {
    private String hotelID;
    private String startDate;
    private String endDate;
}

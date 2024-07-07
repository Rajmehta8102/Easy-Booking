package com.example.hotel.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailableRoomsRequest {
    @NotEmpty(message = "Please enter valid hotel Id")
    private String hotelId;
    private int type;
    @NotEmpty
    private String startDate;
    @NotEmpty
    private String endDate;
}

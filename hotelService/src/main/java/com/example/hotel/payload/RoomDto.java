package com.example.hotel.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomDto {
    private String id;
    @Min(value = 1,message = "Enter Valid Room Type") @Max(value = 3,message = "Enter Valid Room Type")
    private int roomType;
    @NotEmpty(message = "Please Enter Valid room number")
    private String roomNumber;
    private int price;
    @NotEmpty
    private String hotelId;
}


package com.user.service.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingReferenceDto {
        private String id;
        private String roomId;
        private String hotelId;
        private Date startDate;
        private Date endDate;
}
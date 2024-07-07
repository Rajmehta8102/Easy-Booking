package com.user.service.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private String bookingId;
    private String userId;
    private Date startDate;
    private Date endDate;
}

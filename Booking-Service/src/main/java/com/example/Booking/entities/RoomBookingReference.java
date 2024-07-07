package com.example.Booking.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingReference {
    @Id
    private String id;
    private String roomId;
    private String hotelId;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;
    private String userId;
}

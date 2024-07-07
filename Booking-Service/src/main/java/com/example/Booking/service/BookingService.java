package com.example.Booking.service;

import com.example.Booking.entities.RoomBookingReference;
import com.example.Booking.payload.BookingDto;
import com.example.Booking.payload.BookingRequest;
import com.example.Booking.payload.RoomBookingReferenceDto;

import java.util.Date;
import java.util.List;

public interface BookingService {
    BookingDto createBooking(BookingRequest bookingRequest);
    List<RoomBookingReferenceDto> getAllRoomBookingsByHotelId(String hotelId);
    List<RoomBookingReferenceDto> findAllBookedRoomsForGivenDates(String hotelId, Date startDate, Date endDate);

}

package com.example.Booking.controller;


import com.example.Booking.payload.BookedRoomsRequest;
import com.example.Booking.payload.BookingDto;
import com.example.Booking.payload.BookingRequest;
import com.example.Booking.payload.RoomBookingReferenceDto;
import com.example.Booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<BookingDto>createBooking(@Valid @RequestBody BookingRequest bookingRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(bookingRequest));
    }

    @GetMapping("/getReference/hotelId/{hotelId}")
    public ResponseEntity<List<RoomBookingReferenceDto>>getBookingForHotelId(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.FOUND).body(bookingService.getAllRoomBookingsByHotelId(hotelId));
    }

    @PostMapping("/getBookedRooms")
    public ResponseEntity<List<RoomBookingReferenceDto>>getBookedRooms(@Valid @RequestBody BookedRoomsRequest bookingRequest){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(bookingService.findAllBookedRoomsForGivenDates(bookingRequest.getHotelID(),formatter.parse(bookingRequest.getStartDate()),formatter.parse(bookingRequest.getEndDate())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}

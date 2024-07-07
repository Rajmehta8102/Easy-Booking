package com.example.Booking.service.impl;

import com.example.Booking.entities.Booking;
import com.example.Booking.entities.RoomBookingReference;
import com.example.Booking.payload.BookingDto;
import com.example.Booking.payload.BookingRequest;
import com.example.Booking.payload.RoomBookingReferenceDto;
import com.example.Booking.payload.RoomNumber;
import com.example.Booking.repository.BookingRepository;
import com.example.Booking.repository.RoomBookingRepository;
import com.example.Booking.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BookingDto createBooking(BookingRequest bookingRequest) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;
        try {
            startDate  =dateFormatter.parse(bookingRequest.getStartDate());
            endDate =dateFormatter.parse(bookingRequest.getEndDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Booking booking = new Booking();
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setUserId(bookingRequest.getUserId());
        booking.setTotal(bookingRequest.getPrice());
        bookingRepository.save(booking);
        for(RoomNumber room :bookingRequest.getBasic()){
            RoomBookingReference bookingReference = new RoomBookingReference();
            String bookId = UUID.randomUUID().toString();
            bookingReference.setRoomId(room.getRoomNumber());
            bookingReference.setId(bookId);
            bookingReference.setEndDate(endDate);
            bookingReference.setStartDate(startDate);
            bookingReference.setHotelId(bookingRequest.getHotelId());
            bookingReference.setBooking(booking);
            bookingReference.setUserId(bookingRequest.getUserId());
            roomBookingRepository.save(bookingReference);
        }
        for(RoomNumber room :bookingRequest.getPremium()){
            RoomBookingReference bookingReference = new RoomBookingReference();
            String bookId = UUID.randomUUID().toString();
            bookingReference.setRoomId(room.getRoomNumber());
            bookingReference.setId(bookId);
            bookingReference.setEndDate(endDate);
            bookingReference.setStartDate(startDate);
            bookingReference.setHotelId(bookingRequest.getHotelId());
            bookingReference.setBooking(booking);
            bookingReference.setUserId(bookingRequest.getUserId());
            roomBookingRepository.save(bookingReference);
        }
        for(RoomNumber room :bookingRequest.getDeluxe()){
            RoomBookingReference bookingReference = new RoomBookingReference();
            String bookId = UUID.randomUUID().toString();
            bookingReference.setRoomId(room.getRoomNumber());
            bookingReference.setId(bookId);
            bookingReference.setEndDate(endDate);
            bookingReference.setStartDate(startDate);
            bookingReference.setHotelId(bookingRequest.getHotelId());
            bookingReference.setBooking(booking);
            bookingReference.setUserId(bookingRequest.getUserId());
            roomBookingRepository.save(bookingReference);
        }
        return modelMapper.map(booking,BookingDto.class);
    }

    @Override
    public List<RoomBookingReferenceDto> getAllRoomBookingsByHotelId(String hotelId) {
        List<RoomBookingReference> bookings = roomBookingRepository.findAllByHotelId(hotelId);
        return bookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomBookingReferenceDto>getAllRoomBookingsByUserId(String userId){
        List<RoomBookingReference> bookings = roomBookingRepository.findAllByUserId(userId);
        return bookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomBookingReferenceDto> findAllBookedRoomsForGivenDates(String hotelId, Date startDate, Date endDate) {
        List<RoomBookingReference> bookings = roomBookingRepository.findAllBookedRoomsForGivenDates(hotelId,startDate,endDate);
        return bookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private RoomBookingReferenceDto convertToDto(RoomBookingReference booking) {
        return modelMapper.map(booking, RoomBookingReferenceDto.class);
    }
}

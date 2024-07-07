package com.example.hotel.services;

import com.example.hotel.entities.Hotel;
import com.example.hotel.entities.Room;
import com.example.hotel.payload.HotelDto;
import com.example.hotel.payload.Review;

import java.util.List;

public interface HotelService {
    Hotel create(Hotel hotel);
    List<Hotel> getAll();
    HotelDto findById(String id);

    List<Review>fetchReviewsForHotel(String hotelId);

    List<Room> getAllBasicRooms(String hotelId);
    List<Room>getAllPremiumRooms(String hotelId);
    List<Room>getAllDeluxeRooms(String hotelId);
}

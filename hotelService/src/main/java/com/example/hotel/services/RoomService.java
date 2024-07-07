package com.example.hotel.services;

import com.example.hotel.payload.AvailableRoomsRequest;
import com.example.hotel.payload.RoomDto;

import java.util.Date;
import java.util.List;

public interface RoomService {
    RoomDto addRoom(RoomDto roomDto);
    List<RoomDto> findAllByHotelId(String hotelId);
    List<RoomDto>findAllByHotelIdAndType(AvailableRoomsRequest availableRoomsRequest);

}

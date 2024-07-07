package com.example.hotel.repository;

import com.example.hotel.entities.Hotel;
import com.example.hotel.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room,String> {
    List<Room> findAllByHotelId(String hotelId);
    List<Room>findAllByHotelIdAndRoomType(String hotelId,int type);
}

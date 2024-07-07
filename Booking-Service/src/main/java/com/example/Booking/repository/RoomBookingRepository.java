package com.example.Booking.repository;

import com.example.Booking.entities.RoomBookingReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface RoomBookingRepository extends JpaRepository<RoomBookingReference,String> {

    List<RoomBookingReference> findAllByHotelId(String hotelId);

    List<RoomBookingReference> findAllByUserId(String userId);

    @Query("SELECT r FROM RoomBookingReference r WHERE r.startDate >= :startDate AND r.startDate < :endDate AND r.hotelId = :hotelId")
    List<RoomBookingReference> findAllBookedRoomsForGivenDates(@Param("hotelId") String hotelId,
                                                               @Param("startDate") Date startDate,
                                                               @Param("endDate") Date endDate);

}

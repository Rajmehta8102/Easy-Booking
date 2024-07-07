package com.example.hotel.controllers;

import com.example.hotel.payload.AvailableRoomsRequest;
import com.example.hotel.payload.RoomDto;
import com.example.hotel.services.RoomService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/addRoom")
    public ResponseEntity<RoomDto>createRoom(@Valid @RequestBody RoomDto roomDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.addRoom(roomDto));
    }
    @GetMapping("/allRooms/{hotelId}")
    public ResponseEntity<List<RoomDto>>getAllRooms(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.FOUND).body(roomService.findAllByHotelId(hotelId));
    }
    @PostMapping("/availableRooms")
    @CircuitBreaker(name = "bookedRoomsBreaker", fallbackMethod = "bookedRoomsFallBack")
    @Retry(name = "bookedRoomsRetry", fallbackMethod = "bookedRoomsFallBack")
    @RateLimiter(name = "bookedRoomsRateLimiter", fallbackMethod = "bookedRoomsFallBack")
    public ResponseEntity<List<RoomDto>> getAllAvailableRooms(@Valid @RequestBody AvailableRoomsRequest availableRoomsRequest) {
        return ResponseEntity.status(HttpStatus.FOUND).body(roomService.findAllByHotelIdAndType(availableRoomsRequest));
    }

    public ResponseEntity<List<RoomDto>> bookedRoomsFallBack(AvailableRoomsRequest availableRoomsRequest, Throwable throwable) {
        List<RoomDto> roomDtos = new ArrayList<>();
        RoomDto roomDto = new RoomDto();
        roomDto.setRoomType(-1);
        roomDto.setRoomNumber("Booking Service Down");
        roomDto.setHotelId("Booking Service Down");
        roomDto.setId("-1");
        roomDto.setPrice(1);
        roomDtos.add(roomDto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(roomDtos);
    }


}

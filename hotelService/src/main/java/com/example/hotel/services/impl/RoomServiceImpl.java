package com.example.hotel.services.impl;


import com.example.hotel.entities.Hotel;
import com.example.hotel.entities.Room;
import com.example.hotel.exceptions.ResourceNotFoundException;
import com.example.hotel.payload.AvailableRoomsRequest;
import com.example.hotel.payload.RoomBookingReference;
import com.example.hotel.payload.RoomDto;
import com.example.hotel.repository.HotelRepository;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.services.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;

    private Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Override
    public RoomDto addRoom(RoomDto roomdto) {
        Hotel hotel = hotelRepository.findById(roomdto.getHotelId()).orElseThrow(()->new ResourceNotFoundException("Enter Valid HotelId , Hotel Not Found"));
        roomdto.setPrice(addRoomPrice(roomdto.getRoomType()));
        Room room = DtoTomRoom(roomdto);
        room.setHotel(hotel);
        String id = UUID.randomUUID().toString();
        room.setId(id);
        return RoomToDto(roomRepository.save(room));
    }

    @Override
    public List<RoomDto> findAllByHotelId(String hotelId) {
        hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundException("Enter Valid HotelId , Hotel Not Found"));
        List<Room>rooms = this.roomRepository.findAllByHotelId(hotelId);
        return rooms.stream()
                .map(this::RoomToDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<RoomDto>findAllByHotelIdAndType(AvailableRoomsRequest availableRoomsRequest)  {
        List<Room>rooms = roomRepository.findAllByHotelIdAndRoomType(availableRoomsRequest.getHotelId(),availableRoomsRequest.getType());
        String url = "http://BOOKING-SERVICE/booking/getReference/hotelId/" + availableRoomsRequest.getHotelId();
        String jsonResponse = restTemplate.getForObject(url, String.class);
        List<RoomBookingReference> bookingReferenceList = null;
        try {
            bookingReferenceList = mapper.readValue(jsonResponse, new TypeReference<List<RoomBookingReference>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(bookingReferenceList.get(0));
        Date startDate = dateController(availableRoomsRequest.getStartDate());
        Date endDate = dateController(availableRoomsRequest.getEndDate());
        for(int i=0;i<rooms.size();i++){
            Room room = rooms.get(i);
            for(RoomBookingReference booking : bookingReferenceList){
                if(Objects.equals(room.getRoomNumber(), booking.getRoomId()) && (booking.getStartDate().before(startDate) && booking.getEndDate().after(startDate))){
                    rooms.remove(i);
                }
                else if(Objects.equals(room.getRoomNumber(), booking.getRoomId()) && (booking.getStartDate().before(endDate) && booking.getEndDate().after(endDate))){
                    rooms.remove(i);
                }
                else if(Objects.equals(room.getRoomNumber(), booking.getRoomId()) && (booking.getStartDate().after(startDate) && booking.getEndDate().before(endDate))){
                    rooms.remove(i);
                }
            }
        }
        return rooms.stream()
                .map(this::RoomToDto)
                .collect(Collectors.toList());
    }

    private Room DtoTomRoom(RoomDto roomDto){
        return this.modelMapper.map(roomDto,Room.class);
    }

    private RoomDto RoomToDto(Room room){
        return this.modelMapper.map(room,RoomDto.class);
    }

    private int addRoomPrice(int type){
        switch (type){
            case 1: return 3000;
            case 2: return 5000;
            case 3: return 10000;
            default: return 0;
        }
    }

    private Date dateController(String dateString){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateString);
            System.out.println("Parsed Date: " + date);
        } catch (ParseException e) {
            System.out.println("ParseException: " + e.getMessage());
        }
        return date;
    }
}

package com.example.hotel.services.impl;

import com.example.hotel.entities.Hotel;
import com.example.hotel.entities.Room;
import com.example.hotel.exceptions.ResourceNotFoundException;
import com.example.hotel.payload.HotelDto;
import com.example.hotel.payload.Review;
import com.example.hotel.repository.HotelRepository;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.services.HotelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public Hotel create(Hotel hotel) {
        String id = UUID.randomUUID().toString();
        hotel.setId(id);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public HotelDto findById(String id) {
        HotelDto hotelDto = new HotelDto();
        Hotel hotel = hotelRepository.findById(id).orElseThrow(()
                ->new ResourceNotFoundException("Hotel with id"+id+"not found"));
        hotelDto.setLocation(hotel.getLocation());
        hotelDto.setName(hotel.getName());
        return hotelDto;
    }

    @Override
    public List<Review>fetchReviewsForHotel(String hotelId){
        hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel with id"+hotelId+"not found"));
        String url="http://REVIEW-SERVICE/hotelId/"+hotelId;
        String response = restTemplate.getForObject(url,String.class);
        List<Review>reviews = null;
        try {
            reviews = objectMapper.readValue(response, new TypeReference<List<Review>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    @Override
    public List<Room> getAllBasicRooms(String hotelId) {
        return null;
    }

    @Override
    public List<Room> getAllPremiumRooms(String hotelId) {
        return null;
    }

    @Override
    public List<Room> getAllDeluxeRooms(String hotelId) {
        return null;
    }
}

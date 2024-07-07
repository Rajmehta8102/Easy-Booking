package com.user.service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.service.entity.User;
import com.user.service.payload.Review;
import com.user.service.payload.RoomBookingReferenceDto;
import com.user.service.payload.UserDto;
import com.user.service.repository.UserRepository;
import com.user.service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = dtoTOUser(userDto);
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        this.userRepository.save(user);
        userDto.setUserId(userId);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto findUserById(String userId) {
        User user  = this.userRepository.findById(userId).orElseThrow(()->new RuntimeException("Resource With UserId"+userId+"not present"));
        String url = "http://REVIEW-SERVICE/reviews/userId/"+userId;
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = restTemplate.getForObject(url, String.class);
        List<Review>reviews = null;
        try {
            reviews = mapper.readValue(jsonResponse, new TypeReference<List<Review>>() {});
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        System.out.println(reviews);
        UserDto userDto =  userToDto(user);
        userDto.setReviews(reviews);
        String response = restTemplate.getForObject("http://BOOKING-SERVICE/booking/getReference/userId/" + userId, String.class);
        List<RoomBookingReferenceDto>bookings;
        try {
            bookings = mapper.readValue(response, new TypeReference<List<RoomBookingReferenceDto>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        userDto.setBookings(bookings);
        return userDto;
    }

    @Override
    public UserDto updatePassword(String userId, String password) {
        User user  = this.userRepository.findById(userId).orElseThrow(()->new RuntimeException("Resource With UserId"+userId+"not present"));
        this.userRepository.save(user);
        return userToDto(user);
    }

    @Override
    public UserDto updatePhoneNumber(String userId, String mobileNumber) {
        User user  = this.userRepository.findById(userId).orElseThrow(()->new RuntimeException("Resource With UserId"+userId+"not present"));
        user.setMobileNumber(mobileNumber);
        this.userRepository.save(user);
        return userToDto(user);
    }

    private User dtoTOUser(UserDto userDto){
        return this.modelMapper.map(userDto,User.class);
    }

    private UserDto userToDto(User user){
        return this.modelMapper.map(user,UserDto.class);
    }
}

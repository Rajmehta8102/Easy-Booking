package com.user.service.service;

import com.user.service.payload.UserDto;

import java.util.List;

public interface UserService {
    UserDto saveUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto findUserById(String userId);
    UserDto updatePassword(String userId,String password);
    UserDto updatePhoneNumber (String userId,String mobileNumber);

}

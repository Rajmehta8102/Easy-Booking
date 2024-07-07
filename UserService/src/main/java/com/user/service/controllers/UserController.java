package com.user.service.controllers;

import com.user.service.entity.User;
import com.user.service.payload.UserDto;
import com.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<UserDto>(this.userService.saveUser(userDto), HttpStatus.CREATED);
    }
    int count = 0;
    @GetMapping("/userId/{userId}")
    @CircuitBreaker(name = "ratingBookingBreaker")
    @Retry(name = "ratingBookingRetry",fallbackMethod = "ratingBookingFallBack")
    @RateLimiter(name="userRateLimiter",fallbackMethod = "ratingBookingFallBack")
    public ResponseEntity<UserDto> findUserById(@PathVariable String userId){
        count ++;
        return new ResponseEntity<UserDto>(this.userService.findUserById(userId), HttpStatus.OK);
    }

    // creating method for circuit breaker

    public ResponseEntity<UserDto>ratingBookingFallBack(String userId,Exception ex){
        System.out.println(count);
        UserDto user = new UserDto();
        user.setUserId("1234");
        user.setEmail("dummy@gmail.com");
        user.setName("dummy");
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> findAll(){
        return new ResponseEntity<List<UserDto>>(this.userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/userId/{userId}/password/{password}")
    public ResponseEntity<UserDto> updatePassword(@PathVariable String userId,@PathVariable String password){
        return new ResponseEntity<UserDto>(this.userService.updatePassword(userId,password), HttpStatus.OK);
    }
    @PutMapping("/userId/{userId}/mobileNumber/{mobileNumber}")
    public ResponseEntity<UserDto> updateMobileNumber(@PathVariable String userId,@PathVariable String mobileNumber){
        return new ResponseEntity<UserDto>(this.userService.updatePhoneNumber(userId,mobileNumber), HttpStatus.OK);
    }

}

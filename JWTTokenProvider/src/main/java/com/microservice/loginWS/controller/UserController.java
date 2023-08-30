package com.microservice.loginWS.controller;

import com.microservice.loginWS.exception.ExceptionHandling;
import com.microservice.loginWS.mapper.UserMapper;
import com.microservice.loginWS.payload.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends ExceptionHandling {
    private UserMapper userMapper;
    @Autowired
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @PostMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user){
        System.out.println(user.getId());
        System.out.println(user.getFirstName());
        return new ResponseEntity<>(userMapper.updateUser(user), HttpStatus.OK);
    }
}
package com.microservice.loginWS.controller;

import com.microservice.loginWS.exception.ExceptionHandling;
import com.microservice.loginWS.exception.UserNotFoundException;
import com.microservice.loginWS.exception.UsernameExistException;
import com.microservice.loginWS.mapper.SignupMapper;
import com.microservice.loginWS.payload.SignUpDto;
import com.microservice.loginWS.jwtProvider.JwtTokenProvider;
import com.microservice.loginWS.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class SignupController extends ExceptionHandling {
    private IUserService userService;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private SignupMapper signupMapper;
    @Autowired
    public SignupController(IUserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, SignupMapper signupMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.signupMapper = signupMapper;
    }
    @GetMapping("/home")
    public String showUser(){
        return "application works!";
    }

    @PostMapping("/register")
    public ResponseEntity<SignUpDto> register(@RequestBody SignUpDto user) throws UserNotFoundException,  UsernameExistException {
        return new ResponseEntity<>(signupMapper.register(user),HttpStatus.OK);
    }
}
package com.microservice.loginWS.service;

import com.microservice.loginWS.entity.User;
import com.microservice.loginWS.exception.UserNotFoundException;
import com.microservice.loginWS.exception.UsernameExistException;
import com.microservice.loginWS.payload.LoginCredentials;
import com.microservice.loginWS.payload.SignUpDto;
import com.microservice.loginWS.payload.UserDto;

public interface IUserService {

    User register(User user) throws UserNotFoundException, UsernameExistException;

    User findByUsername(String username);

    public User findByUsernameOrEmail(String usernameOrEmail);

    User updateUser(User user);
}

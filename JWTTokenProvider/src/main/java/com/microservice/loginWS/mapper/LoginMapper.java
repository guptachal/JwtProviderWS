package com.microservice.loginWS.mapper;

import com.microservice.loginWS.payload.LoginCredentials;
import com.microservice.loginWS.payload.LoginDto;
import com.microservice.loginWS.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {
    private CommonMapper mapper;
    private IUserService userService;
    @Autowired
    public LoginMapper(CommonMapper mapper, IUserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }
    public LoginDto findUserByUsername(LoginCredentials user){
        return mapper.covertToResponse(userService.findByUsername(user.getUsernameOrEmail()),LoginDto.class);
    }
    public LoginDto login(LoginCredentials credentials){
        return mapper.covertToResponse(userService.findByUsernameOrEmail(credentials.getUsernameOrEmail()), LoginDto.class);
    }
}
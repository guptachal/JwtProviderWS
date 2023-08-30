package com.microservice.loginWS.mapper;

import com.microservice.loginWS.entity.User;
import com.microservice.loginWS.exception.UserNotFoundException;
import com.microservice.loginWS.exception.UsernameExistException;
import com.microservice.loginWS.payload.SignUpDto;
import com.microservice.loginWS.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignupMapper {
    private final CommonMapper mapper;
    private final IUserService service;

    public SignUpDto register(SignUpDto newUser) throws UserNotFoundException, UsernameExistException {
        User usr = service.register(mapper.convertToEntity(newUser,User.class));
        return mapper.covertToResponse(usr, SignUpDto.class);
    }
}

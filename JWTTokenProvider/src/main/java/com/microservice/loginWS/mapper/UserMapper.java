package com.microservice.loginWS.mapper;

import com.microservice.loginWS.entity.User;
import com.microservice.loginWS.payload.UserDto;
import com.microservice.loginWS.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private CommonMapper mapper;
    private IUserService userService;
    @Autowired
    public UserMapper(CommonMapper mapper, IUserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }
    public UserDto updateUser(UserDto user){
        User mediatorUser = userService.updateUser(mapper.convertToEntity(user, User.class));
        return mapper.covertToResponse(mediatorUser,UserDto.class);
    }
}
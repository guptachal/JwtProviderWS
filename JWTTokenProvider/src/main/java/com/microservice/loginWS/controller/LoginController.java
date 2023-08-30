package com.microservice.loginWS.controller;

import com.microservice.loginWS.domain.UserPrincipal;
import com.microservice.loginWS.entity.User;
import com.microservice.loginWS.exception.ExceptionHandling;
import com.microservice.loginWS.mapper.LoginMapper;
import com.microservice.loginWS.payload.LoginCredentials;
import com.microservice.loginWS.payload.LoginDto;
import com.microservice.loginWS.jwtProvider.JwtTokenProvider;
import com.microservice.loginWS.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.microservice.loginWS.utils.AppConstants.JWT_TOKEN_HEADER;

@RestController
@RequestMapping("/user")
public class LoginController extends ExceptionHandling {
    private IUserService userService;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private LoginMapper loginMapper;
    @Autowired
    public LoginController(IUserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, LoginMapper loginMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginMapper = loginMapper;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody LoginCredentials credentials){
        authenticate(credentials.getUsernameOrEmail(),credentials.getPassword());
        User loginUser = userService.findByUsername(credentials.getUsernameOrEmail());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeaders = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginMapper.login(credentials),jwtHeaders, HttpStatus.OK);
    }
    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }
}

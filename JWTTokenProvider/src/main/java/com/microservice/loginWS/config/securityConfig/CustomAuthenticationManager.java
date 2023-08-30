package com.microservice.loginWS.config.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationManager(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails user = userDetailsService.loadUserByUsername(username);
        System.out.println(user.getUsername()+user.getPassword());
        if(user!=null){
            if(passwordEncoder.matches(password,user.getPassword())){
                Authentication token = new UsernamePasswordAuthenticationToken(username,null,getAuthorities(user.getAuthorities()));
                return token;
            }else {
                throw new BadCredentialsException("Password Mismatch!");
            }
        }
        else {
            throw new UsernameNotFoundException("Username not found in the system!");
        }
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for(GrantedAuthority authority: authorities){
            authorityList.add(authority);
        }
        return authorityList;
    }
}
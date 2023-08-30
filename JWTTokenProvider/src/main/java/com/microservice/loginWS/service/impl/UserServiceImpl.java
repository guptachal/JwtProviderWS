package com.microservice.loginWS.service.impl;

import com.microservice.loginWS.domain.UserPrincipal;
import com.microservice.loginWS.entity.User;
import com.microservice.loginWS.mapper.CommonMapper;
import com.microservice.loginWS.repository.IUserRepository;
import com.microservice.loginWS.utils.Roles;
import com.microservice.loginWS.exception.ResourceNotFoundException;
import com.microservice.loginWS.exception.UsernameExistException;
import com.microservice.loginWS.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

@Service
@Transactional
@Qualifier("UserDetailService")
public class UserServiceImpl implements UserDetailsService, IUserService {
    private IUserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CommonMapper mapper;
    @Autowired
    public UserServiceImpl(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, CommonMapper mapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException());
        return new UserPrincipal(user);

    }
    @Override
    public User register(User user) throws UsernameExistException {
        validateNewUsernameAndEmail(user.getUsername(), user.getEmail());
        System.out.println(user.getFirstName()+" "+user.getLastName());
        user.setUserId(generateUserId());
        user.setPassword(encodePassword(user.getPassword()));
        user.setCreatedAt(new Date());
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(Roles.ROLE_USER.name());
        user.setAuthorities(Roles.ROLE_USER.getAuthorities());
        return userRepository.save(user);
    }

    private void validateNewUsernameAndEmail(String username, String email) throws UsernameExistException {
        User userUsingUsername  = userRepository.findUserByUsername(username);

        if(userUsingUsername != null){
            throw new UsernameExistException(" Username already exist!");
        }
    }
    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
    private String generateUserId() {
        Random random = new Random();
        long timestamp = System.currentTimeMillis(); // Get current timestamp in milliseconds
        int randomPart = random.nextInt(100000); // Generate a random 5-digit number
        String uniqueId = String.format("%013d%05d", timestamp, randomPart).substring(0, 10);
        return uniqueId;

    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("username",username,"abs"));
    }
    @Override
    public User findByUsernameOrEmail(String usernameOrEmail){
        return userRepository.findByUsername(usernameOrEmail).orElseThrow(()-> new ResourceNotFoundException());
    }
    @Override
    public User updateUser(User user) {
        System.out.println("userid:" + user.getId());
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found",user.getUsername(),""));

        existingUser.setUpdatedAt(new Date());
        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        return userRepository.save(existingUser);
    }
    private void validateAndUpdatePreExistingUser(User user) {
        userRepository.findByUsername(user.getUsername()).orElseThrow(()-> new ResourceNotFoundException());

    }
}
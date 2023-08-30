package com.microservice.loginWS.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Indexed(unique = true)
    private String id;
    @Indexed(unique = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userId;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String username;
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date lastLoginDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String role;  //ROLE_ADMIN, ROLE_USER
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String[] authorities;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isActive;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isNotLocked;
}
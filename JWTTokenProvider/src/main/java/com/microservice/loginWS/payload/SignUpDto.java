package com.microservice.loginWS.payload;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpDto {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    @Indexed(unique = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String userId;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private Date lastLoginDate;
    private Date createdAt;
    private Date updatedAt;
    private String role;  //ROLE_ADMIN, ROLE_USER
    private String[] authorities; // create, read, update, delete
    private boolean isActive;
    private boolean isNotLocked;

}

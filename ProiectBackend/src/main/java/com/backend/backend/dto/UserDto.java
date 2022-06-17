package com.backend.backend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> roles;


}

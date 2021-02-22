package com.info.finder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private String jwt;
    private String userId;
    private String username;
    private String email;
    private List<String> roles;

}

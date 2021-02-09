package com.info.finder.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Document(collection = "system_user")
public class User {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean active;

    @DBRef
    private Set<Role> roles = new HashSet<>();

}

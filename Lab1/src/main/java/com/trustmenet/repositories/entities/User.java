package com.trustmenet.repositories.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustmenet.repositories.enums.Role;
import com.trustmenet.repositories.enums.UserAccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;

    private String firstName;

    private String secondName;

    private String login;

    private String mail;

    private String password;

    private String profile;

    private Date registrationDate;

    private int rating;

    private UserAccountStatus status;

    private Role role;

    private int imageId;
}


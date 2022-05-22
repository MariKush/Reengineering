package com.trustmenet.repositories.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustmenet.repositories.enums.Role;
import com.trustmenet.repositories.enums.UserAccountStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class UserDto {
    private int id;

    private String firstName;

    private String secondName;

    @Size(min = 6, max = 60)
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "^(.+)@(\\\\S+)$")
    private String mail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 60)
    private String password;

    private String profile;

    private Date registrationDate;

    private int rating;

    private UserAccountStatus status;

    private Role role;

    private int imageId;
}


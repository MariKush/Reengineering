package com.trustmenet.repositories.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
public class UserAchievement {
    @EqualsAndHashCode.Exclude
    private int id;
    private int userId;
    @EqualsAndHashCode.Exclude
    private UserDto user;
    private int achievementId;
    @EqualsAndHashCode.Exclude
    private Achievement achievement;
    @EqualsAndHashCode.Exclude
    private Date date;
}

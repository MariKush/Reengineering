package com.trustmenet.repositories.dto;


import com.trustmenet.repositories.entities.Achievement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
public class UserAchievementDto {
    private int id;
    private int userId;
    private UserDto user;
    private int achievementId;
    private AchievementDto achievement;
    private Date date;
}

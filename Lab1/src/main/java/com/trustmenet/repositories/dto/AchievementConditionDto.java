package com.trustmenet.repositories.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchievementConditionDto {

    private int id;
    private ConditionOperatorDto operator;
    private int value;
    private int achievementId;
    private int characteristicId;
    private AchievementCharacteristicDto characteristic;
}

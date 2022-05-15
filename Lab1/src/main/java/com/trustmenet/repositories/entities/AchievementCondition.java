package com.trustmenet.repositories.entities;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchievementCondition {
    @EqualsAndHashCode.Exclude
    private int id;
    private ConditionOperator operator;
    private int value;
    private int achievementId;
    private int characteristicId;
    @EqualsAndHashCode.Exclude
    private AchievementCharacteristic characteristic;
}

package com.trustmenet.repositories.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Achievement {
    private int id;
    private String name;
    private String description;
    private List<AchievementCondition> achievementConditions;
}

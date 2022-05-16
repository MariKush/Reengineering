package com.trustmenet.repositories.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Achievement {
    private int id;
    private String name;
    private String description;
    @NotEmpty
    private List<AchievementCondition> achievementConditions;
}

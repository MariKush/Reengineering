package com.trustmenet.repositories.dto;

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
public class AchievementDto {
    private int id;
    private String name;
    private String description;
    @NotEmpty
    private List<AchievementConditionDto> achievementConditions;
}

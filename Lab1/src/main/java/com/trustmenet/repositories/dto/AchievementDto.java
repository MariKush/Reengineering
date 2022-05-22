package com.trustmenet.repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchievementDto {
    private int id;
    @Size(min = 6, max = 60)
    private String name;
    @Size(min = 6, max = 100)
    private String description;
    @NotEmpty
    private List<AchievementConditionDto> achievementConditions;
}

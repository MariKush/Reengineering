package com.trustmenet.repositories.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchievementCharacteristicDto {
    private int id;
    private String name;
    private String sqlScript;
}

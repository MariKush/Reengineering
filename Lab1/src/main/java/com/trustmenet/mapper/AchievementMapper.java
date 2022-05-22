package com.trustmenet.mapper;


import com.trustmenet.repositories.dto.AchievementDto;
import com.trustmenet.repositories.entities.Achievement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementMapper extends GenericMapper<AchievementDto, Achievement> {
}

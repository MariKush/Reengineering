package com.trustmenet.mapper;


import com.trustmenet.repositories.dto.AchievementCharacteristicDto;
import com.trustmenet.repositories.entities.AchievementCharacteristic;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AchievementCharacteristicMapper extends GenericMapper<AchievementCharacteristicDto, AchievementCharacteristic> {
}

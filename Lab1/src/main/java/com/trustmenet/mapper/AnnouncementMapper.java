package com.trustmenet.mapper;


import com.trustmenet.repositories.dto.AnnouncementDto;
import com.trustmenet.repositories.entities.Announcement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnnouncementMapper extends GenericMapper<AnnouncementDto, Announcement> {
}

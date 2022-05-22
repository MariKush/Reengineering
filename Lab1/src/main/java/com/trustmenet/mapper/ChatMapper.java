package com.trustmenet.mapper;


import com.trustmenet.repositories.dto.ChatDto;
import com.trustmenet.repositories.dto.UserDto;
import com.trustmenet.repositories.entities.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper extends GenericMapper<ChatDto, Chat> {
}

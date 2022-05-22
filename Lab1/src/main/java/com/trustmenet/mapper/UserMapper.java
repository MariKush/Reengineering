package com.trustmenet.mapper;


import com.trustmenet.repositories.dto.UserDto;
import com.trustmenet.repositories.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends GenericMapper<UserDto, User> {
}

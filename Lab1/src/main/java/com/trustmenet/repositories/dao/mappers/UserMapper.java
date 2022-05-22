package com.trustmenet.repositories.dao.mappers;

import com.trustmenet.repositories.entities.UserDto;
import com.trustmenet.repositories.entities.enums.Role;
import com.trustmenet.repositories.entities.enums.UserAccountStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserDto> {

    @Override
    public UserDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return UserDto.builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .secondName(resultSet.getString("second_name"))
                .login(resultSet.getString("login"))
                .mail(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .profile(resultSet.getString("profile"))
                .registrationDate(resultSet.getDate("registered_date"))
                .rating(resultSet.getInt("rating"))
                .status(UserAccountStatus.valueOf(resultSet.getString("status").toUpperCase()))
                .role(Role.valueOf(resultSet.getString("role").toUpperCase()))
                .imageId(resultSet.getInt("image_id"))
                .build();
    }
}

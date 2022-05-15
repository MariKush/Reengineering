package com.trustmenet.repositories.dao.mappers;


import com.trustmenet.repositories.entities.AchievementCharacteristic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AchievementCharacteristicMapper implements RowMapper<AchievementCharacteristic> {
    @Override
    public AchievementCharacteristic mapRow(ResultSet resultSet, int i) throws SQLException {
        return AchievementCharacteristic.builder()
                .id(resultSet.getInt("id"))
                .sqlScript(resultSet.getString("script"))
                .name(resultSet.getString("name"))
                .build();
    }
}

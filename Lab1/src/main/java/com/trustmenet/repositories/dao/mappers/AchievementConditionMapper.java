package com.trustmenet.repositories.dao.mappers;


import com.trustmenet.repositories.entities.AchievementCondition;
import com.trustmenet.repositories.entities.ConditionOperator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AchievementConditionMapper implements RowMapper<AchievementCondition> {
    @Override
    public AchievementCondition mapRow(ResultSet resultSet, int i) throws SQLException {
        return AchievementCondition.builder()
                .id(resultSet.getInt("id"))
                .operator(ConditionOperator.valueOf(resultSet.getString("operator").toUpperCase()))
                .value(resultSet.getInt("value"))
                .achievementId(resultSet.getInt("achievement_id"))
                .characteristicId(resultSet.getInt("achievement_characteristic_id"))
                .build();
    }
}

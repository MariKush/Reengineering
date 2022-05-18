package com.trustmenet.repositories.dao.mappers.extractors;


import com.trustmenet.repositories.entities.Achievement;
import com.trustmenet.repositories.entities.AchievementCharacteristic;
import com.trustmenet.repositories.entities.AchievementCondition;
import com.trustmenet.repositories.entities.ConditionOperator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AchievementExtractor implements ResultSetExtractor<List<Achievement>> {

    @Override
    public List<Achievement> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Achievement> achievementMap = new HashMap<>();
        Map<Integer, AchievementCharacteristic> achievementCharacteristicMap = new HashMap<>();

        while (rs.next()) {
            int achievementId = rs.getInt("achievement_id");
            Achievement achievement = achievementMap.get(achievementId);

            if (achievement == null) {
                achievement = buildAchievement(rs, achievementId);
                achievementMap.put(achievementId, achievement);
            }

            int characteristicId = rs.getInt("achievement_characteristic_id");
            AchievementCharacteristic characteristic = achievementCharacteristicMap.get(characteristicId);

            if (characteristic == null) {
                characteristic = buildAchievementCharacterisitc(rs, characteristicId);
                achievementCharacteristicMap.put(characteristicId, characteristic);
            }

            AchievementCondition condition = buildAchievementCondition(rs, achievementId, characteristicId, characteristic);

            achievement.getAchievementConditions().add(condition);
        }

        return new ArrayList<>(achievementMap.values());
    }

    private Achievement buildAchievement(ResultSet rs, int achievementId) throws SQLException {
        return Achievement.builder()
                .id(achievementId)
                .name(rs.getString("achievement_name"))
                .description(rs.getString("description"))
                .achievementConditions(new ArrayList<>())
                .build();
    }

    private AchievementCharacteristic buildAchievementCharacterisitc(ResultSet rs, int characteristicId) throws SQLException {
        return AchievementCharacteristic.builder()
                .id(characteristicId)
                .name(rs.getString("achievement_characteristic_name"))
                .sqlScript(rs.getString("script"))
                .build();
    }

    private AchievementCondition buildAchievementCondition(ResultSet rs, int achievementId, int characteristicId, AchievementCharacteristic characteristic) throws SQLException {
        return AchievementCondition.builder()
                .achievementId(achievementId)
                .id(rs.getInt("achievement_condition_id"))
                .operator(ConditionOperator.valueOf(rs.getString("operator").toUpperCase()))
                .characteristicId(characteristicId)
                .value(rs.getInt("value"))
                .characteristic(characteristic)
                .build();
    }
}


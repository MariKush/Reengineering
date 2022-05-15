package com.trustmenet.repositories.dao.implementation;


import com.trustmenet.repositories.dao.AchievementDao;
import com.trustmenet.repositories.dao.mappers.AchievementMapper;
import com.trustmenet.repositories.dao.mappers.UserAchievementMapper;
import com.trustmenet.repositories.dao.mappers.extractors.AchievementExtractor;
import com.trustmenet.repositories.entities.Achievement;
import com.trustmenet.repositories.entities.AchievementCondition;
import com.trustmenet.repositories.entities.UserAchievement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
@PropertySource("classpath:achievement.properties")
public class AchievementDaoImpl extends GenericDaoImpl<Achievement> implements AchievementDao {

    @Value("#{${sql.achievement}}")
    private Map<String, String> achievementQueries;


    protected AchievementDaoImpl() {
        super(new AchievementMapper(), TABLE_NAME);
    }

    @Override
    public List<Achievement> getAll() {
        return jdbcTemplate.query(
                achievementQueries.get("getAll"), new AchievementExtractor()
        );
    }

    @Override
    protected String getInsertQuery() {
        return achievementQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement,
                                                           Achievement achievement) throws SQLException {
        preparedStatement.setString(1, achievement.getDescription());
        preparedStatement.setString(2, achievement.getName());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return achievementQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(Achievement achievement) {
        return new Object[]{achievement.getDescription(),
                achievement.getName(),
                achievement.getId()};
    }

    private String generateCondition(Achievement achievement) {
        List<String> conditions = new ArrayList<>();
        for (AchievementCondition condition : achievement.getAchievementConditions()) {
            conditions.add(String.format("( %s ) %s %s",
                    condition.getCharacteristic().getSqlScript(),
                    condition.getOperator().getOperatorSymbol(),
                    condition.getValue()));
        }
        return String.join(" AND ", conditions);
    }

    @Override
    public List<UserAchievement> getNewUserAchievements(List<Achievement> achievements) {
        String sqlT = achievementQueries.get("getNewUserAchievements");
        List<String> sql = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        for (Achievement achievement : achievements) {
            sql.add(String.format(sqlT, generateCondition(achievement)));
            objects.add(achievement.getId());
        }

        return jdbcTemplate.query(String.join(" UNION ALL ", sql), objects.toArray(),
                new UserAchievementMapper());
    }
}


package com.trustmenet.repositories.dao.implementation;


import com.trustmenet.repositories.dao.mappers.AchievementCharacteristicMapper;
import com.trustmenet.repositories.entities.AchievementCharacteristic;
import com.trustmenet.repositories.dao.AchievementCharacteristicDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Repository
@PropertySource("classpath:achievement.properties")
public class AchievementCharacteristicDaoImpl extends GenericDaoImpl<AchievementCharacteristic> implements AchievementCharacteristicDao {

    @Value("#{${sql.achievementCharacteristic}}")
    private Map<String, String> achievementConditionQueries;

    protected AchievementCharacteristicDaoImpl() {
        super(new AchievementCharacteristicMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return achievementConditionQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement,
                                                           AchievementCharacteristic achievementCharacteristic) throws SQLException {
        preparedStatement.setString(1, achievementCharacteristic.getName());
        preparedStatement.setString(2, achievementCharacteristic.getSqlScript());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return achievementConditionQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(AchievementCharacteristic achievementCharacteristic) {
        return new Object[]{achievementCharacteristic.getName(),
                achievementCharacteristic.getSqlScript(), achievementCharacteristic.getId()};
    }

}

package com.trustmenet.repositories.dao;


import com.trustmenet.repositories.dao.GenericDao;
import com.trustmenet.repositories.entities.AchievementCharacteristic;

public interface AchievementCharacteristicDao extends GenericDao<AchievementCharacteristic> {
    String TABLE_NAME = "achievement_characteristic";
}

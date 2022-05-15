package com.trustmenet.repositories.dao;


import com.trustmenet.repositories.entities.Achievement;
import com.trustmenet.repositories.entities.UserAchievement;

import java.util.List;

public interface AchievementDao extends GenericDao<Achievement>{
    String TABLE_NAME = "achievement";

    List<UserAchievement> getNewUserAchievements(List<Achievement> achievements);
}

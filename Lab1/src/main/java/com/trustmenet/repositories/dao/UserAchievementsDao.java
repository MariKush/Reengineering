package com.trustmenet.repositories.dao;


import com.trustmenet.repositories.entities.Achievement;
import com.trustmenet.repositories.entities.UserAchievement;

import java.util.List;

public interface UserAchievementsDao extends GenericDao<UserAchievement>{
    String TABLE_NAME = "user_achievements_list";

    List<Achievement> getAchievementsByUserId(int userId);

    void delete(List<Integer> userAchievements);

    void insert(List<UserAchievement> userAchievements);
}

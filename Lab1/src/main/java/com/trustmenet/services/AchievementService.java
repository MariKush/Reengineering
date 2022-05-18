package com.trustmenet.services;


import com.trustmenet.repositories.dao.AchievementCharacteristicDao;
import com.trustmenet.repositories.dao.AchievementConditionDao;
import com.trustmenet.repositories.dao.AchievementDao;
import com.trustmenet.repositories.dao.UserAchievementsDao;
import com.trustmenet.repositories.dao.implementation.UserAchievementsDaoImpl;
import com.trustmenet.repositories.entities.Achievement;
import com.trustmenet.repositories.entities.AchievementCharacteristic;
import com.trustmenet.repositories.entities.AchievementCondition;
import com.trustmenet.repositories.entities.UserAchievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AchievementService {

    @Autowired
    private UserAchievementsDao userAchievementsDao;

    @Autowired
    private AchievementCharacteristicDao achievementCharacteristicDao;

    @Autowired
    private AchievementDao achievementDao;

    @Autowired
    private AchievementConditionDao achievementConditionDao;


    public List<Achievement> getAchievementsByUserId(int id) {
        return userAchievementsDao.getAchievementsByUserId(id);
    }

    public List<AchievementCharacteristic> getAllAchievementCharacteristics() {
        return achievementCharacteristicDao.getAll();
    }

    public boolean createAchievement(Achievement achievement) {
        int achievementId = achievementDao.save(achievement);

        for (AchievementCondition achievementCondition : achievement.getAchievementConditions()) {
            achievementCondition.setAchievementId(achievementId);
        }

        achievementConditionDao.insert(achievement.getAchievementConditions());

        return true;
    }

    public void recalculateAchievements() {
        List<Achievement> achievements = achievementDao.getAll();
        List<UserAchievement> beforeUpdateAchievements = userAchievementsDao.getAll();
        List<UserAchievement> afterUpdateAchievements = achievementDao.getNewUserAchievements(achievements);

        if (afterUpdateAchievements.equals(beforeUpdateAchievements)) {
            return;
        }

        insertNewAchievements(beforeUpdateAchievements, afterUpdateAchievements);
        deleteOldAchievements(beforeUpdateAchievements, afterUpdateAchievements);
    }

    private void insertNewAchievements(List<UserAchievement> beforeUpdateAchievements, List<UserAchievement> afterUpdateAchievements) {
        List<UserAchievement> toInsert = afterUpdateAchievements.stream()
                .filter(userAchievement -> !beforeUpdateAchievements.contains(userAchievement))
                .collect(Collectors.toList());
        if (!toInsert.isEmpty()) {
            userAchievementsDao.insert(toInsert);
        }
    }

    private void deleteOldAchievements(List<UserAchievement> beforeUpdateAchievements, List<UserAchievement> afterUpdateAchievements) {
        List<Integer> toDelete = beforeUpdateAchievements.stream()
                .filter(userAchievement -> !afterUpdateAchievements.contains(userAchievement))
                .map(UserAchievement::getAchievementId)
                .collect(Collectors.toList());

        if (!toDelete.isEmpty()) {
            userAchievementsDao.delete(toDelete);
        }
    }
}

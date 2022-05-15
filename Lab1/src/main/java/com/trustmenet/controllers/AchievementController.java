package com.trustmenet.controllers;


import com.trustmenet.repositories.entities.Achievement;
import com.trustmenet.repositories.entities.AchievementCharacteristic;
import com.trustmenet.services.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @GetMapping("/profile/{id}/achievements/")
    public List<Achievement> getUserAchievement(@PathVariable int id) {
        return achievementService.getAchievementsByUserId(id);
    }

    @GetMapping("/achievement/characteristics")
    public List<AchievementCharacteristic> getAchievementCharacteristics() {
        return achievementService.getAllAchievementCharacteristics();
    }

    @PostMapping("/achievement/create")
    public boolean createAchievement(@RequestBody Achievement achievement) {
        return achievementService.createAchievement(achievement);
    }

    @PutMapping("/achievement/recalculate")
    public void recalculateUserAchievements() {
        achievementService.recalculateAchievements();
    }

}

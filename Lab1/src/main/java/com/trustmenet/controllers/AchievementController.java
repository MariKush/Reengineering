package com.trustmenet.controllers;


import com.trustmenet.repositories.dto.AchievementCharacteristicDto;
import com.trustmenet.repositories.dto.AchievementDto;
import com.trustmenet.services.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @GetMapping("/profile/{id}/achievements/")
    public List<AchievementDto> getUserAchievement(@PathVariable int id) {
        return achievementService.getAchievementsByUserId(id);
    }

    @GetMapping("/achievement/characteristics")
    public List<AchievementCharacteristicDto> getAchievementCharacteristics() {
        return achievementService.getAllAchievementCharacteristics();
    }

    @PostMapping("/achievement/create")
    public boolean createAchievement(@RequestBody @Valid AchievementDto achievement) {
        return achievementService.createAchievement(achievement);
    }

    @PutMapping("/achievement/recalculate")
    public void recalculateUserAchievements() {
        achievementService.recalculateAchievements();
    }

}

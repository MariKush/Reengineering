package com.trustmenet.services;

import com.trustmenet.repositories.dao.AnnouncementDao;
import com.trustmenet.repositories.entities.Announcement;
import com.trustmenet.repositories.entities.UserDto;
import com.trustmenet.repositories.entities.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@PropertySource("classpath:user.properties")
public class AnnouncementService {
    @Autowired
    private AnnouncementDao announcementDao;

    @Autowired
    private UserService userService;

    @Value("#{${defaultRatingIncrease}}")
    private int ratingIncreaseValue;

    @Value("#{${defaultRatingMin}}")
    private int ratingMinValue;

    public int createAnnouncement(Announcement announcement) {
        UserDto author = userService.getUserById(announcement.getAuthorId());
        if (author.getRole() != Role.USER || author.getRating() >= ratingMinValue) {
            announcement.setPublished(true);
            userService.increaseUserRating(announcement.getAuthorId(), ratingIncreaseValue);
        }
        else
            announcement.setPublished(false);

        int announcementId = announcementDao.save(announcement);
        if (announcementId == -1) {
            log.info("createAnnouncement: Announcement wasn't saved");
            return -1;
        }
        return announcementId;
    }

    public void updateAnnouncement(Announcement announcement) {
        if (announcement == null) {
            log.info("updateAnnouncement: Announcement is null");
            return;
        }
        Announcement originalAnnouncement = announcementDao.getById(announcement.getId());
        if (!originalAnnouncement.isPublished() && announcement.isPublished()){
            userService.increaseUserRating(announcement.getAuthorId(), ratingIncreaseValue);
        }
        announcementDao.update(announcement);
    }

    public void deleteAnnouncement(int announcementId) {
        announcementDao.deleteById(announcementId);
    }

    public Announcement getAnnouncementById(int announcementId) {
        return announcementDao.getById(announcementId);
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementDao.getAllInfo();
    }


    public List<Announcement> getAllAnnouncements(boolean isPublished) {
        return announcementDao.getAllInfo(isPublished);
    }


}


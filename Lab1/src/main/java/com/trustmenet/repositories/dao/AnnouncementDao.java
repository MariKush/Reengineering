package com.trustmenet.repositories.dao;

import com.trustmenet.repositories.entities.Announcement;

import java.util.List;

public interface AnnouncementDao extends GenericDao<Announcement> {
    String TABLE_NAME = "announcement";

    List<Announcement> getAllInfo();

    Announcement getById(int id);

    List<Announcement> getAllInfo(boolean isPublished);

}

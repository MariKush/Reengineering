package com.trustmenet.repositories.dao;

import com.trustmenet.repositories.entities.Image;

public interface ImageDao extends GenericDao<Image> {
    String TABLE_NAME = "image";

    int getIdBySrc(String src);
}
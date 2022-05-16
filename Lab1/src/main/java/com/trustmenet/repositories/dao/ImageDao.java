package com.trustmenet.repositories.dao;

import com.trustmenet.repositories.entities.Image;

import java.util.Optional;

public interface ImageDao extends GenericDao<Image> {
    String TABLE_NAME = "image";

    Optional<Integer> getIdBySrc(String src);
}
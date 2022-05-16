package com.trustmenet.services;


import com.trustmenet.repositories.dao.implementation.ImageDaoImpl;
import com.trustmenet.repositories.entities.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class ImageService {

    @Autowired
    private ImageDaoImpl imageDao;

    private final List<String> validContentTypes = List.of("image/png", "image/jpeg", "image/gif");

    public int addImage(MultipartFile multipartFile) {
        if (!validContentTypes.contains(multipartFile.getContentType())) {
            throw new UnsupportedOperationException("File has incorrect content type " + multipartFile.getContentType());
        }

        try {
            byte[] fileBytes = multipartFile.getBytes();
            return saveImage(Base64.getEncoder().encodeToString(fileBytes));
        } catch (IOException e) {
            log.error("Error while get bytes of file", e);
            throw new UnsupportedOperationException("Error while get bytes of file ", e);
        }
    }

    public int saveImage(String encodedFile) {
        Optional<Integer> imageId = imageDao.getIdBySrc(encodedFile);
        log.info("imageId: " + imageId);

        return imageId.orElseGet(() -> imageDao.save(Image.builder().src(encodedFile).build()));
    }
}


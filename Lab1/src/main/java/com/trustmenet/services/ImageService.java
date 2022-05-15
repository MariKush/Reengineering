package com.trustmenet.services;


import com.trustmenet.repositories.dao.implementation.ImageDaoImpl;
import com.trustmenet.repositories.entities.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@PropertySource("classpath:application.properties")
public class ImageService {

    @Autowired
    private ImageDaoImpl imageDao;

    private final List<String> validContentTypes = List.of("image/png", "image/jpeg", "image/gif");

    public int addImage(MultipartFile multipartFile) {
        String encodedFile;
        log.info("image");
        if (multipartFile == null) {
            log.error("addImage: multipartFile is null");
            return -1;
        }
        if (!validContentTypes.contains(multipartFile.getContentType())) {
            throw new UnsupportedOperationException("File has incorrect content type " + multipartFile.getContentType());
        }
        byte[] fileBytes;
        try {
            fileBytes = multipartFile.getBytes();
        } catch (IOException e) {
            log.error("Error while get bytes of file", e);
            return -1;
        }
        encodedFile = Base64.getEncoder().encodeToString(fileBytes);

        return saveImage(encodedFile);
    }

    public int saveImage(String encodedFile) {
        int imageId = imageDao.getIdBySrc(encodedFile);
        log.info("kkk" + imageId);
        if (imageId != -1) {
            return imageId;
        }
        return imageDao.save(Image.builder().src(encodedFile).build());
    }


}


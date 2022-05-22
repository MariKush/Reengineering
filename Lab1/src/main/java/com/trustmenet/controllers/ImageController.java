package com.trustmenet.controllers;

import com.trustmenet.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PutMapping
    public int saveImage(@RequestParam("myFile") @NotNull MultipartFile file) {
        return imageService.addImage(file);
    }

    @GetMapping("/{id}")
    public byte[] getImage(@PathVariable int id){
        return imageService.getImage(id);
    }
}

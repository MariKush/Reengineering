package com.trustmenet.repositories.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDto {
    private int id;
    private String authorLogin;
    private int authorId;

    @JsonProperty("isPublished")
    private boolean isPublished;

    private Date createdDate;
    private String title;
    private String subtitle;
    private String fullText;
    private int imageId;
}

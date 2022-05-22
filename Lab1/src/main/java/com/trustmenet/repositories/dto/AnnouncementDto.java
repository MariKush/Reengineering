package com.trustmenet.repositories.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
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
    @Size(min = 6, max = 100)
    private String title;
    @Size(min = 6, max = 100)
    private String subtitle;
    @Size(min = 6, max = 1000)
    private String fullText;
    private int imageId;
}

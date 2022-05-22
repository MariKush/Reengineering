package com.trustmenet.repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private int id;
    private int chatId;
    private int authorId;
    private UserDto author;
    private Timestamp creationDate;
    @Size(min = 6, max = 1000)
    private String messageText;
}

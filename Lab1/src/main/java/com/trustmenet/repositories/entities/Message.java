package com.trustmenet.repositories.entities;

import com.trustmenet.repositories.entities.UserDto;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class Message {
    private int id;
    private int chatId;
    private int authorId;
    private UserDto author;
    private Timestamp creationDate;
    private String messageText;
}

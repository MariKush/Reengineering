package com.trustmenet.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int id;
    private int chatId;
    private int authorId;
    private User author;
    private Timestamp creationDate;
    private String messageText;
}

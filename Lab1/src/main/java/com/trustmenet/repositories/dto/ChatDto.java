package com.trustmenet.repositories.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private int id;
    private String name;
    private Timestamp creationDate;
    private List<UserDto> users;
}

package com.trustmenet.repositories.entities;

import com.trustmenet.repositories.entities.enums.TokenType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Token {
    private String token;
    private TokenType tokenType;
    private Date expiredDate;
    private int userId;
}

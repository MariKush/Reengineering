package com.trustmenet.repositories.dto;

import com.trustmenet.repositories.enums.TokenType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TokenDto {
    private String token;
    private TokenType tokenType;
    private Date expiredDate;
    private int userId;
}

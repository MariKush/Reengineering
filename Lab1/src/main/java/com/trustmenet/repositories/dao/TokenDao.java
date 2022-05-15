package com.trustmenet.repositories.dao;

import com.trustmenet.repositories.entities.Token;

public interface TokenDao {
    Token get(int id);
    int getUserId(Token token);
    void deleteOldTokens();
    int save(Token token);
    void deleteToken(Token token);
}

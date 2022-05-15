package com.trustmenet.services;

import com.trustmenet.repositories.dao.TokenDao;
import com.trustmenet.repositories.entities.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenDao tokenDao;

    public void saveToken(Token token){
        tokenDao.save(token);
    }

    public int getUserId(Token token) {
        return tokenDao.getUserId(token);
    }

    public void delete(Token token) {
        tokenDao.deleteToken(token);
    }
}

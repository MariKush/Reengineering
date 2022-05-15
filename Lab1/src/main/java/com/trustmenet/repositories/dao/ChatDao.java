package com.trustmenet.repositories.dao;

import com.trustmenet.repositories.entities.Chat;

import java.util.List;

public interface ChatDao extends GenericDao<Chat> {
    String TABLE_NAME = "chat";
    List<Chat> getAllFullInfoForUser(int id);
    Chat getFullInfo(int id);
    void addChatMember(int chatId, int userId);
    void removeChatMember(int chatId, int userId);
}

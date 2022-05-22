package com.trustmenet.services;

import com.trustmenet.mapper.ChatMapper;
import com.trustmenet.repositories.dao.ChatDao;
import com.trustmenet.repositories.dto.ChatDto;
import com.trustmenet.repositories.entities.Chat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatService {
    @Autowired
    private ChatDao chatDao;

    @Autowired
    private ChatMapper chatMapper;

    public List<ChatDto> getAllChatsForUser(int userId) {
        validateUserIdNotZero(userId);
        return chatMapper.toDto(chatDao.getAllFullInfoForUser(userId));
    }


    public ChatDto getFullChatInfo(int chatId) {
        validateChatId(chatId);
        return chatMapper.toDto(chatDao.getFullInfo(chatId));
    }

    public void addMemberToChat(int chatId, int userId) {
        validateUserIdNotZero(userId);
        validateChatId(chatId);
        chatDao.addChatMember(chatId, userId);
    }

    public void removeMemberFromChat(int chatId, int userId) {
        validateChatId(chatId);
        validateUserIdNotZero(userId);
        chatDao.removeChatMember(chatId, userId);
    }

    private void validateChatId(int chatId) {
        validateIdNotZero(chatId, "Chat id can`t be 0", "0 can`t be chat`s identifier");
    }

    private void validateUserIdNotZero(int userId) {
        validateIdNotZero(userId, "User id can`t be 0", "0 can`t be user`s identifier");
    }

    private void validateIdNotZero(int userId, String s, String s2) {
        if (userId == 0) {
            log.warn(s);
            throw new IllegalArgumentException(s2);
        }
    }

    public int createChat(ChatDto chatDto, int userId) {
        Chat chat = chatMapper.toEntity(chatDto);
        int chatId = chatDao.save(chat);
        if (chatId != -1) {
            chatDao.addChatMember(chatId, userId);
        }
        return chatId;
    }

    public void updateChat(ChatDto chatDto) {
        Chat chat = chatMapper.toEntity(chatDto);
        chatDao.update(chat);
    }
}

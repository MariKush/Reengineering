package com.trustmenet.services;

import com.trustmenet.repositories.dao.MessageDao;
import com.trustmenet.repositories.entities.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public Message saveMessage(Message message) {
        int messageId = messageDao.save(message);
        return messageDao.get(messageId);
    }

    public Page<Message> getPageMessages(int chatId, int page, int size) {
        if (chatId == 0) {
            log.warn("Chat id can`t be 0");
            throw new IllegalArgumentException("0 can`t be chat`s identifier");
        }
        return messageDao.getMessagesFromChat(chatId,
                PageRequest.of(page, size, Sort.Direction.ASC, "creationDate"));
    }
}

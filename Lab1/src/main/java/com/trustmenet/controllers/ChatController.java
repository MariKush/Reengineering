package com.trustmenet.controllers;

import com.trustmenet.repositories.entities.Chat;
import com.trustmenet.services.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/users/{userId}/chats")
    public List<Chat> getUserChats(@PathVariable int userId) {
        return chatService.getAllChatsForUser(userId);
    }

    @GetMapping("/chat/{chatId}")
    public Chat getChatById(@PathVariable int chatId) {
        return chatService.getFullChatInfo(chatId);
    }

    @PostMapping("/users/{id}/chat/{chatId}/invite")
    public void addMemberToChat(@PathVariable int id, @PathVariable int chatId) {
        chatService.addMemberToChat(chatId, id);
    }

    @PutMapping("/chat")
    public void updateChat(@RequestBody Chat chat) {
        chatService.updateChat(chat);
    }

    @PostMapping("/users/{id}/createChat")
    public int createChat(@PathVariable int id, @RequestBody Chat chat) {
        return chatService.createChat(chat, id);
    }

    @DeleteMapping("/users/{id}/chat/{chatId}")
    public void leaveChat(@PathVariable int id, @PathVariable int chatId) {
        chatService.removeMemberFromChat(chatId, id);
    }
}

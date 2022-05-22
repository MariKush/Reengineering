package com.trustmenet.controllers;

import com.trustmenet.repositories.dto.ChatDto;
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
    public List<ChatDto> getUserChats(@PathVariable int userId) {
        return chatService.getAllChatsForUser(userId);
    }

    @GetMapping("/chat/{chatId}")
    public ChatDto getChatById(@PathVariable int chatId) {
        return chatService.getFullChatInfo(chatId);
    }

    @PostMapping("/users/{id}/chat/{chatId}/invite")
    public void addMemberToChat(@PathVariable int id, @PathVariable int chatId) {
        chatService.addMemberToChat(chatId, id);
    }

    @PutMapping("/chat")
    public void updateChat(@RequestBody ChatDto chat) {
        chatService.updateChat(chat);
    }

    @PostMapping("/users/{id}/createChat")
    public int createChat(@PathVariable int id, @RequestBody ChatDto chat) {
        return chatService.createChat(chat, id);
    }

    @DeleteMapping("/users/{id}/chat/{chatId}")
    public void leaveChat(@PathVariable int id, @PathVariable int chatId) {
        chatService.removeMemberFromChat(chatId, id);
    }
}

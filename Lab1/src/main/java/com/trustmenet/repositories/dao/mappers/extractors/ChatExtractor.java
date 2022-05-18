package com.trustmenet.repositories.dao.mappers.extractors;

import com.trustmenet.repositories.entities.UserDto;
import com.trustmenet.repositories.entities.Chat;
// import com.trustmenet.repositories.entities.Image;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatExtractor implements ResultSetExtractor<List<Chat>> {
    @Override
    public List<Chat> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Chat> chatMap = new HashMap<>();
        Map<Integer, UserDto> userMap = new HashMap<>();

        while (rs.next()) {
            int chatId = rs.getInt("chat_id");
            Chat chat = chatMap.get(chatId);
            if (chat == null) {
                chat = buildChat(rs, chatId);
                chatMap.put(chatId, chat);
            }

            List<UserDto> users = chat.getUsers();

            int userId = rs.getInt("user_id");
            if (userId != 0) {
                UserDto user = userMap.get(userId);
                if (user == null) {
                    user = buildUser(rs, userId);
                    userMap.put(userId, user);
                }
                users.add(user);
            }
        }
        return new ArrayList<>(chatMap.values());
    }

    private UserDto buildUser(ResultSet rs, int userId) throws SQLException {
        return UserDto.builder()
                .id(userId)
                .login(rs.getString("login"))
                .firstName(rs.getString("first_name"))
                .secondName(rs.getString("second_name"))
                .mail(rs.getString("email"))
                .imageId(rs.getInt("image_id"))
                .build();
    }

    private Chat buildChat(ResultSet rs, int chatId) throws SQLException {
        return Chat.builder()
                .id(chatId)
                .name(rs.getString("name"))
                .creationDate(rs.getTimestamp("creation_date"))
                .users(new ArrayList<>())
                .build();
    }
}

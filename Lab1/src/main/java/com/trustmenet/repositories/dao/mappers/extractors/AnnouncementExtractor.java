package com.trustmenet.repositories.dao.mappers.extractors;

import com.trustmenet.repositories.entities.Announcement;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnouncementExtractor implements ResultSetExtractor<List<Announcement>> {
    @Override
    public List<Announcement> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Announcement> announcements = new HashMap<>();

        while (resultSet.next()) {
            Announcement announcement = Announcement.builder()
                    .id(resultSet.getInt("id"))
                    .authorLogin(resultSet.getString("author_login"))
                    .authorId(resultSet.getInt("author_id"))
                    .isPublished(resultSet.getBoolean("is_published"))
                    .title(resultSet.getString("title"))
                    .subtitle(resultSet.getString("subtitle"))
                    .fullText(resultSet.getString("full_text"))
                    .createdDate(resultSet.getTimestamp("created_date"))
                    .imageId(resultSet.getInt("image_id"))
                    .build();
            announcements.put(resultSet.getInt("id"), announcement);
        }

        return new ArrayList<>(announcements.values());
    }
}

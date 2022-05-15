package com.trustmenet.repositories.dao.implementation;


import com.trustmenet.repositories.dao.AnnouncementDao;
import com.trustmenet.repositories.dao.mappers.AnnouncementMapper;
import com.trustmenet.repositories.dao.mappers.extractors.AnnouncementExtractor;
import com.trustmenet.repositories.entities.Announcement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@PropertySource("classpath:announcement.properties")
public class AnnouncementDaoImpl extends GenericDaoImpl<Announcement> implements AnnouncementDao {
    @Value("#{${sql.announcement}}")
    private Map<String, String> announcementQueries;

    protected AnnouncementDaoImpl() {
        super(new AnnouncementMapper(), TABLE_NAME);
    }

    @Override
    protected String getInsertQuery() {
        return announcementQueries.get("insert");
    }

    @Override
    protected PreparedStatement getInsertPreparedStatement(PreparedStatement preparedStatement, Announcement announcement) throws SQLException {
        preparedStatement.setInt(1, announcement.getAuthorId());
        preparedStatement.setBoolean(2, announcement.isPublished());
        preparedStatement.setString(3, announcement.getTitle());
        preparedStatement.setString(4, announcement.getSubtitle());
        preparedStatement.setString(5, announcement.getFullText());
        preparedStatement.setInt(6, announcement.getImageId());
        return preparedStatement;
    }

    @Override
    protected String getUpdateQuery() {
        return announcementQueries.get("update");
    }

    @Override
    protected Object[] getUpdateParameters(Announcement announcement) {
        return new Object[]{announcement.isPublished(), announcement.getTitle(),
                announcement.getSubtitle(), announcement.getFullText(), announcement.getImageId(), announcement.getId()};
    }

    @Override
    public List<Announcement> getAllInfo() {
        return jdbcTemplate.query(announcementQueries.get("getAllInfo"), new AnnouncementMapper());
    }

    @Override
    public Announcement getById(int id) {
        List<Announcement> result = jdbcTemplate.query(
                announcementQueries.get("getAllInfo").replace("ORDER BY a.created_date DESC;", " WHERE a.id = ?;"),
                new Object[]{id}, new AnnouncementExtractor()
        );
        return result.size() == 0 ? null : result.get(0);
    }


    @Override
    public List<Announcement> getAllInfo(boolean isPublished) {
        return jdbcTemplate.query(
                announcementQueries.get("getAllInfo").replace("ORDER BY a.created_date DESC;", " WHERE is_published = ? ORDER BY a.created_date DESC;"),
                new Object[]{isPublished},
                new AnnouncementMapper());
    }
}

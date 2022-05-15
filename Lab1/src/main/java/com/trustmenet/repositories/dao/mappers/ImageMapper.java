package com.trustmenet.repositories.dao.mappers;

import com.trustmenet.repositories.entities.Image;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageMapper implements RowMapper<Image> {
    @Override
    public Image mapRow(ResultSet resultSet, int i) throws SQLException {
        return Image.builder()
                .id(resultSet.getInt("id"))
                .src(resultSet.getString("src"))
                .build();
    }
}

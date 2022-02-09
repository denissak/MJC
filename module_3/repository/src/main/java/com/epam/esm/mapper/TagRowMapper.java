package com.epam.esm.mapper;

import com.epam.esm.entity.TagEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagRowMapper implements RowMapper<TagEntity> {

    @Override
    public TagEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TagEntity.builder()
                .id(rs.getLong(1))
                .name(rs.getString(2))
                .build();
    }
}

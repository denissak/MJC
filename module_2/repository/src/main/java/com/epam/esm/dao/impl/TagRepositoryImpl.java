package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private static final String GET_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?";
    private static final String GET_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?";
    private static final String GET_ALL_TAGS = "SELECT * FROM tag";
    private static final String SAVE_TAG = "INSERT INTO tag (name) VALUES (?)";
    private static final String DELETE_TAG = "DELETE FROM tag WHERE id = ?";

    private JdbcTemplate jdbcTemplate;
    private TagRowMapper tagRowMapper;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SAVE_TAG, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tag.getName());
            return preparedStatement;
        }, keyHolder);
        Long id = (Long) keyHolder.getKeyList().get(0).get("id");
        tag.setId(id);
        return tag;
    }

    @Override
    public Optional<Tag> readById(Long id) {
        List<Tag> tagList = jdbcTemplate.query(GET_TAG_BY_ID, tagRowMapper, id);
        if (tagList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(tagList.get(0));
    }

    @Override
    public Optional<Tag> readByName(String name) {
        List<Tag> tagList = jdbcTemplate.query(GET_TAG_BY_NAME, tagRowMapper, name);
        if (tagList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(tagList.get(0));
    }

    @Override
    public List<Tag> readAll() {
        return jdbcTemplate.query(GET_ALL_TAGS, tagRowMapper);
    }

    @Override
    public Integer delete(Long id) {
        return jdbcTemplate.update(DELETE_TAG, id);
    }
}

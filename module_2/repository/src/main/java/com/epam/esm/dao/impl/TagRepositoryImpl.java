package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class TagRepositoryImpl implements TagRepository {

    private JdbcTemplate jdbcTemplate;
    private TagRowMapper tagRowMapper;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public Tag create(Tag model) {
        return null;
    }

    @Override
    public Optional<Tag> readById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Tag> readByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Tag> readAll() {
        return null;
    }

    @Override
    public Integer delete(Long id) {
        return null;
    }
}

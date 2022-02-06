package com.epam.esm.dao.impl;

import com.epam.esm.configuration.ApplicationConfigurationTest;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringJUnitConfig(ApplicationConfigurationTest.class)
    @SqlGroup({@Sql(scripts = "/dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
public class TagRepositoryImplTest {

    private static final Long TAG_ID_1 = 1L;
    private static final Long TAG_ID_2 = 2L;
    private static final Long TAG_ID_3 = 3L;
    private static final Long TAG_ID_4 = 4L;
    private static final String TAG_NAME = "tag2";

    @Autowired
    TagRepository tagRepository;

    private Tag tag1;
    private Tag tag2;
    private Tag tag3;
    private Tag tag4;

    @BeforeEach
    public void setUp() {
        tag1 = Tag.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();

        tag2 = Tag.builder()
                .id(TAG_ID_2)
                .name("tag2")
                .build();

        tag3 = Tag.builder()
                .id(TAG_ID_3)
                .name("tag3")
                .build();

        tag4 = Tag.builder()
                .id(TAG_ID_4)
                .name("tag4")
                .build();
    }

    @Test
    void testCreate() {
        Tag actual = tagRepository.create(tag4);
        Assertions.assertEquals(tag4, actual);
    }

    @Test
    void readById() {
        Optional<Tag> actual = tagRepository.readById(TAG_ID_1);
        Assertions.assertEquals(Optional.of(tag1), actual);
    }

    @Test
    void readByName() {
        Optional<Tag> actual = tagRepository.readByName(TAG_NAME);
        Assertions.assertEquals(Optional.of(tag2), actual);
    }

    @Test
    void readAll() {
        List<Tag> actual = tagRepository.readAll();
        List<Tag> expected = Arrays.asList(tag1, tag2, tag3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        int actual = tagRepository.delete(TAG_ID_1);
        Assertions.assertEquals(1, actual);
    }
}


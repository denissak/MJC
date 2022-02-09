package com.epam.esm.dao.impl;

import com.epam.esm.configuration.ApplicationConfigurationTest;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.TagEntity;
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
public class TagEntityRepositoryImplTest {

    private static final Long TAG_ID_1 = 1L;
    private static final Long TAG_ID_2 = 2L;
    private static final Long TAG_ID_3 = 3L;
    private static final Long TAG_ID_4 = 4L;
    private static final String TAG_NAME = "tag2";

    @Autowired
    TagRepository tagRepository;

    private TagEntity tagEntity1;
    private TagEntity tagEntity2;
    private TagEntity tagEntity3;
    private TagEntity tagEntity4;

    @BeforeEach
    public void setUp() {
        tagEntity1 = TagEntity.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();

        tagEntity2 = TagEntity.builder()
                .id(TAG_ID_2)
                .name("tag2")
                .build();

        tagEntity3 = TagEntity.builder()
                .id(TAG_ID_3)
                .name("tag3")
                .build();

        tagEntity4 = TagEntity.builder()
                .id(TAG_ID_4)
                .name("tag4")
                .build();
    }

    @Test
    void testCreate() {
        TagEntity actual = tagRepository.create(tagEntity4);
        Assertions.assertEquals(tagEntity4, actual);
    }

    @Test
    void readById() {
        Optional<TagEntity> actual = tagRepository.readById(TAG_ID_1);
        Assertions.assertEquals(Optional.of(tagEntity1), actual);
    }

    @Test
    void readByName() {
        Optional<TagEntity> actual = tagRepository.readByName(TAG_NAME);
        Assertions.assertEquals(Optional.of(tagEntity2), actual);
    }

    @Test
    void readAll() {
        List<TagEntity> actual = tagRepository.readAll();
        List<TagEntity> expected = Arrays.asList(tagEntity1, tagEntity2, tagEntity3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        int actual = tagRepository.delete(TAG_ID_1);
        Assertions.assertEquals(1, actual);
    }
}

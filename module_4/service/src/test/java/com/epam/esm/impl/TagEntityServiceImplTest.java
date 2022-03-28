package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.TagMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagEntityServiceImplTest {

    private static TagMapper tagMapper;
    private TagRepository tagRepository;
    private CertificateRepository certificateRepository;
    private static TagService tagServiceImpl;
    private static final Long TAG_ID_1 = 1L;

    private TagEntity tagEntity;
    private TagDto tagDto;

    private List<TagEntity> tagEntityList = new ArrayList<>();
    private List<TagDto> tagDtoList = new ArrayList<>();

    private Page<TagEntity> tagEntityPage;
    private Page<TagDto> tagDtoPage;

    private Pageable pageable;

    @BeforeAll
    public static void init() {
        tagMapper = TagMapper.INSTANCE;
    }

    @BeforeEach
    public void setUp() {
        certificateRepository = mock(CertificateRepository.class);
        tagRepository = mock(TagRepository.class);
        tagServiceImpl = new TagServiceImpl(tagRepository, tagMapper, certificateRepository);

        tagEntity = TagEntity.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();

        tagDto = TagDto.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();

        pageable = PageRequest.of(0,1000, Sort.by("id"));

        tagEntityList.add(tagEntity);
        tagDtoList.add(tagDto);

        tagEntityPage = new PageImpl<>(tagEntityList);
        tagDtoPage = new PageImpl<>(tagDtoList);

    }

    @Test
    void testCreate() {
        TagDto expected = tagDto;
        when(tagRepository.save(any())).thenReturn(tagEntity);
        TagDto actual = tagServiceImpl.create(expected);
        Assertions.assertEquals(expected, actual);
        verify(tagRepository).save(any());
    }

    @Test
    void testReadById() {
        TagDto expected = tagDto;
        when(tagRepository.findById(TAG_ID_1)).thenReturn(Optional.ofNullable(tagEntity));
        TagDto actual = tagServiceImpl.readById(TAG_ID_1);
        Assertions.assertEquals(expected, actual);
        verify(tagRepository).findById(anyLong());
    }

    @Test
    void testReadAll() {
        List<TagDto> expected = tagDtoList;
        when(tagRepository.findAll(pageable)).thenReturn(tagEntityPage);
        List<TagDto> actual = tagServiceImpl.readAll(0,1000);
        Assertions.assertEquals(expected, actual);
        verify(tagRepository).findAll(pageable);
    }

    @Test
    void testDeleteTag() {
        when(tagRepository.findById(TAG_ID_1)).thenReturn(Optional.ofNullable(tagEntity));
        tagServiceImpl.delete(TAG_ID_1);
        verify(tagRepository).delete(tagEntity);
    }

    @Test
    void getMostPopularTag() {
        tagServiceImpl.getMostPopularTag();
        verify(tagRepository).getMostPopularTag();
    }
}

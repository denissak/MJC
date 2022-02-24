package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.TagMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @BeforeAll
    public static void init () {
        tagMapper = TagMapper.INSTANCE;
    }

    @BeforeEach
    public void setUp () {
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
    }

    @Test
    void testCreate() {
        TagDto expected = tagDto;
        when(tagRepository.create(any())).thenReturn(tagEntity);
        TagDto actual = tagServiceImpl.create(expected);
        Assertions.assertEquals(expected, actual);
        verify(tagRepository).create(any());
    }

    @Test
    void testReadById() {
        TagDto expected = tagDto;
        when(tagRepository.readById(TAG_ID_1)).thenReturn(tagEntity);
        TagDto actual = tagServiceImpl.readById(TAG_ID_1);
        Assertions.assertEquals(expected, actual);
        verify(tagRepository).readById(anyLong());
    }

    @Test
    void testReadAll() {
        tagServiceImpl.readAll(anyInt(), anyInt());
        verify(tagRepository).readAll(anyInt(), anyInt());
    }

    @Test
    void readException() {
        Assertions.assertThrows(NotFoundException.class, () -> tagServiceImpl.readById(TAG_ID_1));
    }

    @Test
    void testDeleteCertificate() {
        when(tagRepository.readById(TAG_ID_1)).thenReturn(tagEntity);
//        when(tagRepository.delete(TAG_ID_1)).thenReturn(1);
        tagServiceImpl.delete(TAG_ID_1);
        verify(tagRepository).delete(anyLong());
//        verify(tagRepository).delete(TAG_ID_1);
    }
}

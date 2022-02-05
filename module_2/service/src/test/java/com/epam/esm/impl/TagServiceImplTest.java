package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class TagServiceImplTest {
    private static TagMapper tagMapper;
    private TagRepository tagRepository;
    private CertificateRepository certificateRepository;
    private static TagService tagServiceImpl;
    private static final Long TAG_ID_1 = 1L;

    private Tag tag;
    private TagDto tagDto;

    @BeforeAll
    public static void init () {
        tagMapper = TagMapper.INSTANCE;
    }

    @BeforeEach
    public void setUp () {
        certificateRepository = Mockito.mock(CertificateRepository.class);
        tagRepository = Mockito.mock(TagRepository.class);
        tagServiceImpl = new TagServiceImpl(tagRepository, tagMapper, certificateRepository);

        tag = Tag.builder()
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
        Mockito.when(tagRepository.create(Mockito.any())).thenReturn(tag);
        TagDto actual = tagServiceImpl.create(expected);
        Assertions.assertEquals(expected, actual);
        Mockito.verify(tagRepository).create(Mockito.any());
    }

    @Test
    void testReadById() {
        TagDto expected = tagDto;
        Mockito.when(tagRepository.readById(TAG_ID_1)).thenReturn(Optional.of(tag));
        TagDto actual = tagServiceImpl.readById(TAG_ID_1);
        Assertions.assertEquals(expected, actual);
        Mockito.verify(tagRepository).readById(Mockito.anyLong());
    }

    @Test
    void testReadAll() {
        tagServiceImpl.readAll();
        Mockito.verify(tagRepository).readAll();
    }

    @Test
    void readException() {
        Assertions.assertThrows(NotFoundException.class, () -> tagServiceImpl.readById(TAG_ID_1));
    }

    @Test
    void testDeleteCertificate() {
        when(tagRepository.readById(TAG_ID_1)).thenReturn(Optional.of(tag));
        when(tagRepository.delete(TAG_ID_1)).thenReturn(1);
        tagServiceImpl.delete(TAG_ID_1);
        verify(tagRepository).delete(TAG_ID_1);
    }
}

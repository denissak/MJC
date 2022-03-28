package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.CertificateMapperImpl;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.mapper.TagMapperImpl;
import com.epam.esm.util.DateTimeWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CertificateEntityServiceImplTest {

    private static final Long CERTIFICATE_ID_1 = 1L;
    private static final Long TAG_ID_1 = 1111L;
    private static final String ANY_STRING = "";
    private static final Integer ANY_INTEGER = 1;
    private static final String[] ANY_MASSIVE = new String[1];
    private CertificateEntity certificateEntity;
    private CertificateDto certificateDto;
    private TagEntity tagEntity;
    private TagDto tagDto;
    private List<CertificateEntity> certificateEntityList = new ArrayList<>();
    private List<CertificateDto> certificateDtoList = new ArrayList<>();
    private Pageable pageable;
    private Page<CertificateEntity> certificateEntityPage;

    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private DateTimeWrapper dateTimeWrapper;
    private static CertificateMapper certificateMapper = new CertificateMapperImpl(new TagMapperImpl());
    private static TagMapper tagMapper = new TagMapperImpl();
    private static LocalDateTime localDateTime;

    private CertificateService certificateServiceImpl;

    @BeforeAll
    public static void init() {
        certificateMapper = new CertificateMapperImpl(tagMapper);
        tagMapper = new TagMapperImpl();
        localDateTime = LocalDateTime.now();
    }

    @BeforeEach
    public void setUp() {
        certificateServiceImpl = new CertificateServiceImpl(certificateRepository, tagRepository, certificateMapper, tagMapper, dateTimeWrapper);

        tagEntity = TagEntity.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();

        tagDto = TagDto.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();

        certificateEntity = CertificateEntity.builder()
                .id(CERTIFICATE_ID_1)
                .name("Some certificate")
                .description("test certificate")
                .price(8.0)
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(90)
                .tagEntities(List.of(tagEntity))
                .build();

        certificateDto = CertificateDto.builder()
                .id(CERTIFICATE_ID_1)
                .name("Some certificate")
                .description("test certificate")
                .price(8.0)
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(90)
                .tags(List.of(tagDto))
                .build();

        certificateEntityList.add(certificateEntity);
        certificateDtoList.add(certificateDto);
        certificateEntityPage = new PageImpl<>(certificateEntityList);
        pageable = PageRequest.of(0, 1000, Sort.by("id"));
    }

    @Test
    void testCreate() {
        CertificateDto expected = certificateDto;
        expected.setTags(List.of(tagDto));
        when(certificateRepository.save(any())).thenReturn(certificateEntity);
        when(dateTimeWrapper.wrapDateTime()).thenReturn(localDateTime);
        when(tagRepository.save(any())).thenReturn(tagEntity);
        when(tagRepository.readByName(anyString())).thenReturn(null);
        CertificateDto actual = certificateServiceImpl.create(expected);
        assertEquals(expected, actual);
        verify(certificateRepository).save(any());
        verify(dateTimeWrapper).wrapDateTime();
        verify(tagRepository).save(any());
        verify(tagRepository).readByName(any());
    }

    @Test
    void testReadAllCertificates() {
        List<CertificateDto> expected = certificateDtoList;
        when(certificateRepository.findAll(pageable)).thenReturn(certificateEntityPage);
        List<CertificateDto> actual = certificateServiceImpl.readAll(0, 1000);
        assertEquals(expected, actual);
        verify(certificateRepository).findAll(pageable);
    }

    @Test
    void testReadCertificateById() {
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.ofNullable(certificateEntity));
        certificateServiceImpl.readById(certificateEntity.getId());
    }

    @Test
    void testDeleteCertificate() {
        when(certificateRepository.findById(CERTIFICATE_ID_1)).thenReturn(Optional.ofNullable(certificateEntity));
        certificateServiceImpl.delete(CERTIFICATE_ID_1);
        verify(certificateRepository).delete(certificateEntity);
    }

    @Test
    void readCertificateWithDifferentParams() {
        List<CertificateEntity> certificateEntities = List.of(certificateEntity);
        certificateDto.setTags(List.of(tagDto));
        List<CertificateDto> expected = List.of(certificateDto);
        when(certificateRepository.readCertificateWithDifferentParams(any(), anyString(), anyString(),
                anyString(), anyString(), anyInt(), anyInt())).thenReturn(certificateEntities);
        List<CertificateDto> actual = certificateServiceImpl.readCertificateWithDifferentParams(ANY_MASSIVE, ANY_STRING, ANY_STRING, ANY_STRING, ANY_STRING, ANY_INTEGER, ANY_INTEGER);
        assertEquals(expected, actual);
        verify(certificateRepository).readCertificateWithDifferentParams(any(), anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt());
    }

    @Test
    void update() {
        CertificateDto expected = certificateDto;
        expected.setTags(List.of(tagDto));
        certificateRepository.save(any());
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.ofNullable(certificateEntity));
        when(dateTimeWrapper.wrapDateTime()).thenReturn(localDateTime);
        when(certificateRepository.findById(CERTIFICATE_ID_1)).thenReturn(Optional.ofNullable(certificateEntity));
        tagRepository.findById(TAG_ID_1);
        CertificateDto actual = certificateServiceImpl.update(CERTIFICATE_ID_1, expected);
        assertEquals(expected, actual);
        verify(certificateRepository).save(certificateEntity);
        verify(dateTimeWrapper).wrapDateTime();
    }
}

package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.util.DateTimeWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CertificateEntityServiceImplTest {

    private static final Long CERTIFICATE_ID_1 = 1L;
    private static final Long TAG_ID_1 = 1L;
    private static final Long INVALID_ID = -1L;
    private static final String ANY_STRING = "";
    private CertificateEntity certificateEntity;
    private CertificateDto certificateDto;
    private TagEntity tagEntity;
    private TagDto tagDto;

    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private DateTimeWrapper dateTimeWrapper;
    private static CertificateMapper certificateMapper;
    private static TagMapper tagMapper;
    private static LocalDateTime localDateTime;

    private CertificateService certificateServiceImpl;

    @BeforeAll
    public static void init() {
        certificateMapper = CertificateMapper.INSTANCE;
        tagMapper = TagMapper.INSTANCE;
        localDateTime = LocalDateTime.now();
    }

    @BeforeEach
    public void setUp() {
        certificateServiceImpl = new CertificateServiceImpl(certificateRepository, tagRepository, certificateMapper, tagMapper, dateTimeWrapper );

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
                .price(new BigDecimal("8"))
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(90)
                .tagEntities(List.of(tagEntity))
                .build();

        certificateDto = CertificateDto.builder()
                .id(CERTIFICATE_ID_1)
                .name("Some certificate")
                .description("test certificate")
                .price(new BigDecimal("8"))
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(90)
                .tags(List.of(tagDto))
                .build();
    }

    @Test
    void testCreate() {
        CertificateDto expected = certificateDto;
        expected.setTags(List.of(tagDto));
        when(certificateRepository.create(any())).thenReturn(certificateEntity);
        when(dateTimeWrapper.wrapDateTime()).thenReturn(localDateTime);
        when(tagRepository.create(any())).thenReturn(tagEntity);
        when(tagRepository.readByName(any())).thenReturn(null);
        CertificateDto actual = certificateServiceImpl.create(expected);
        assertEquals(expected, actual);
        verify(certificateRepository).create(any());
        verify(dateTimeWrapper).wrapDateTime();
        verify(tagRepository).create(any());
        verify(tagRepository).readByName(any());
    }

    @Test
    void testReadAllCertificates() {
        List<CertificateEntity> certificateEntities = List.of(certificateEntity);
        certificateDto.setTags(List.of(tagDto));
        List<CertificateDto> expected = List.of(certificateDto);
        when(certificateRepository.readAll()).thenReturn(certificateEntities);
        when(certificateRepository.readCertificateTags(anyLong())).thenReturn(certificateEntity.getTagEntities());
        List<CertificateDto> actual = certificateServiceImpl.readAll();
        assertEquals(expected, actual);
        verify(certificateRepository).readAll();
        verify(certificateRepository).readCertificateTags(anyLong());
    }

    @Test
    void testReadByIdWithInvalidId() {
        assertThrows(NotFoundException.class, () -> certificateServiceImpl.readById(INVALID_ID));
    }

    @Test
    void testReadCertificateById() {
        when(certificateRepository.readById(anyLong())).thenReturn(Optional.of(certificateEntity));
        certificateServiceImpl.readById(certificateEntity.getId());
        verify(certificateRepository).readCertificateTags(certificateEntity.getId());
    }

    @Test
    void testUpdateNotFoundException() {
        assertThrows(NotFoundException.class, () -> certificateServiceImpl.update(CERTIFICATE_ID_1, certificateDto));
    }

    @Test
    void testDeleteCertificate() {
        when(certificateRepository.readById(CERTIFICATE_ID_1)).thenReturn(Optional.of(certificateEntity));
//        when(certificateRepository.delete(CERTIFICATE_ID_1)).thenReturn(1);
        certificateServiceImpl.delete(CERTIFICATE_ID_1);
        verify(certificateRepository).delete(CERTIFICATE_ID_1);
    }

    @Test
    void readCertificateWithDifferentParams() {
        List<CertificateEntity> certificateEntities = List.of(certificateEntity);
        certificateDto.setTags(List.of(tagDto));
        List<CertificateDto> expected = List.of(certificateDto);
        when(certificateRepository.readCertificateWithDifferentParams(anyString(),anyString(),anyString(),anyString(),anyString())).thenReturn(certificateEntities);
        when(certificateRepository.readCertificateTags(anyLong())).thenReturn(certificateEntity.getTagEntities());
        List<CertificateDto> actual = certificateServiceImpl.readCertificateWithDifferentParams(ANY_STRING, ANY_STRING, ANY_STRING, ANY_STRING, ANY_STRING);
        assertEquals(expected, actual);
        verify(certificateRepository).readCertificateWithDifferentParams(anyString(),anyString(),anyString(),anyString(),anyString());
    }

    @Test
    void update() {
        CertificateDto expected = certificateDto;
        expected.setTags(List.of(tagDto));
        certificateRepository.update(anyLong(), any());
        when(certificateRepository.readById(anyLong())).thenReturn(Optional.of(certificateEntity));
        when(dateTimeWrapper.wrapDateTime()).thenReturn(localDateTime);
        when(tagRepository.readByName(any())).thenReturn(null);
        when(tagRepository.create(any())).thenReturn(tagEntity);
        when(certificateRepository.readById(CERTIFICATE_ID_1)).thenReturn(Optional.of(certificateEntity));
        tagRepository.readById(TAG_ID_1);
        CertificateDto actual = certificateServiceImpl.update(CERTIFICATE_ID_1, expected);
        assertEquals(expected, actual);
        verify(certificateRepository).update(CERTIFICATE_ID_1, certificateEntity);
        verify(dateTimeWrapper).wrapDateTime();
    }
}

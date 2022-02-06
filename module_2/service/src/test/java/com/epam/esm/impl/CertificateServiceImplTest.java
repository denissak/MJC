package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
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
public class CertificateServiceImplTest {

    private static final Long CERTIFICATE_ID_1 = 1L;
    private static final Long TAG_ID_1 = 1L;
    private static final Long INVALID_ID = -1L;
    private static final String ANY_STRING = "";
    private Certificate certificate;
    private CertificateDto certificateDto;
    private Tag tag;
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

        tag = Tag.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();

        tagDto = TagDto.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();

        certificate = Certificate.builder()
                .id(CERTIFICATE_ID_1)
                .name("Some certificate")
                .description("test certificate")
                .price(new BigDecimal("8"))
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(90)
                .tags(List.of(tag))
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
        when(certificateRepository.create(any())).thenReturn(certificate);
        when(dateTimeWrapper.wrapDateTime()).thenReturn(localDateTime);
        when(tagRepository.create(any())).thenReturn(tag);
        when(tagRepository.readByName(any())).thenReturn(Optional.empty());
        CertificateDto actual = certificateServiceImpl.create(expected);
        assertEquals(expected, actual);
        verify(certificateRepository).create(any());
        verify(dateTimeWrapper).wrapDateTime();
        verify(tagRepository).create(any());
        verify(tagRepository).readByName(any());
    }

    @Test
    void testReadAllCertificates() {
        List<Certificate> certificates = List.of(certificate);
        certificateDto.setTags(List.of(tagDto));
        List<CertificateDto> expected = List.of(certificateDto);
        when(certificateRepository.readAll()).thenReturn(certificates);
        when(certificateRepository.readCertificateTags(anyLong())).thenReturn(certificate.getTags());
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
        when(certificateRepository.readById(anyLong())).thenReturn(Optional.of(certificate));
        certificateServiceImpl.readById(certificate.getId());
        verify(certificateRepository).readCertificateTags(certificate.getId());
    }

    @Test
    void testUpdateNotFoundException() {
        assertThrows(NotFoundException.class, () -> certificateServiceImpl.update(CERTIFICATE_ID_1, certificateDto));
    }

    @Test
    void testDeleteCertificate() {
        when(certificateRepository.readById(CERTIFICATE_ID_1)).thenReturn(Optional.of(certificate));
        when(certificateRepository.delete(CERTIFICATE_ID_1)).thenReturn(1);
        certificateServiceImpl.delete(CERTIFICATE_ID_1);
        verify(certificateRepository).delete(CERTIFICATE_ID_1);
    }

    @Test
    void readCertificateWithDifferentParams() {
        List<Certificate> certificates = List.of(certificate);
        certificateDto.setTags(List.of(tagDto));
        List<CertificateDto> expected = List.of(certificateDto);
        when(certificateRepository.readCertificateWithDifferentParams(anyString(),anyString(),anyString(),anyString(),anyString())).thenReturn(certificates);
        when(certificateRepository.readCertificateTags(anyLong())).thenReturn(certificate.getTags());
        List<CertificateDto> actual = certificateServiceImpl.readCertificateWithDifferentParams(ANY_STRING, ANY_STRING, ANY_STRING, ANY_STRING, ANY_STRING);
        assertEquals(expected, actual);
        verify(certificateRepository).readCertificateWithDifferentParams(anyString(),anyString(),anyString(),anyString(),anyString());
    }

    @Test
    void update() {
        CertificateDto expected = certificateDto;
        expected.setTags(List.of(tagDto));
        certificateRepository.update(anyLong(), any());
        when(certificateRepository.readById(anyLong())).thenReturn(Optional.of(certificate));
        when(dateTimeWrapper.wrapDateTime()).thenReturn(localDateTime);
        when(tagRepository.readByName(any())).thenReturn(Optional.empty());
        when(tagRepository.create(any())).thenReturn(tag);
        when(certificateRepository.readById(CERTIFICATE_ID_1)).thenReturn(Optional.of(certificate));
        tagRepository.readById(TAG_ID_1);
        CertificateDto actual = certificateServiceImpl.update(CERTIFICATE_ID_1, expected);
        assertEquals(expected, actual);
        verify(certificateRepository).update(CERTIFICATE_ID_1, certificate);
        verify(dateTimeWrapper).wrapDateTime();
    }
}

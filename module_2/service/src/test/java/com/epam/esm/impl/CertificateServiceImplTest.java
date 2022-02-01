package com.epam.esm.impl;

import com.epam.esm.CertificateService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceImplTest {

    private static final Long CERTIFICATE_ID_1 = 1L;
    private static final Long TAG_ID_1 = 1L;
    private static final Long INVALID_ID = -1L;
    private Certificate certificate;
    private CertificateDto certificateDto;
    private Tag tag;
    private TagDto tagDto;


    private CertificateRepository certificateRepository;
    private TagRepository tagRepository;
    private static CertificateMapper certificateMapper;
    private static TagMapper tagMapper;
    private static LocalDateTime localDateTime;

    private CertificateService certificateServiceImpl;

    @BeforeAll
    public static void init() {
        certificateMapper = CertificateMapper.INSTANCE;
        tagMapper = TagMapper.INSTANCE;
        localDateTime = localDateTime.now();
    }

    @BeforeEach
    public void setUp() {
        certificateRepository = Mockito.mock(CertificateRepository.class);
        tagRepository = Mockito.mock(TagRepository.class);

        certificateServiceImpl = new CertificateServiceImpl(certificateRepository, tagRepository, certificateMapper, tagMapper);

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
                .name("Some sertificate")
                .description("test certificate")
                .price(new BigDecimal("8.00"))
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(90)
                .tags(List.of(tag))
                .build();

        certificateDto = CertificateDto.builder()
                .id(CERTIFICATE_ID_1)
                .name("Some sertificate")
                .description("test certificate")
                .price(new BigDecimal("8.00"))
                .createDate(localDateTime)
                .lastUpdateDate(localDateTime)
                .duration(90)
                .tags(List.of(tag))
                .build();


    }

}

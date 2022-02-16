package com.epam.esm.dao.impl;

import com.epam.esm.configuration.ApplicationConfigurationTest;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringJUnitConfig(ApplicationConfigurationTest.class)
@SqlGroup({@Sql(scripts = "/dropTables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
public class CertificateEntityRepositoryImplTest {
    private static final Long CERTIFICATE_ID_1 = 1L;
    private static final Long CERTIFICATE_ID_2 = 2L;
    private static final Long CERTIFICATE_ID_3 = 3L;
    private static final Long CERTIFICATE_ID_4 = 4L;
    private static final Long TAG_ID_1 = 1L;
    private static final Long TAG_ID_3 = 3L;

    private static final String CERTIFICATE_NAME = "cert1";

    private CertificateEntity certificateEntity1;
    private CertificateEntity certificateEntity2;
    private CertificateEntity certificateEntity3;
    private CertificateEntity certificateEntity4;
    private TagEntity tagEntity1;
    private TagEntity tagEntity3;

    @BeforeEach
    public void setUp() {
        certificateEntity1 = CertificateEntity.builder()
                .id(CERTIFICATE_ID_1)
                .name("cert1")
                .description("nice")
                .price(new BigDecimal("5"))
                .createDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .lastUpdateDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .duration(90)
                .build();

        certificateEntity2 = CertificateEntity.builder()
                .id(CERTIFICATE_ID_2)
                .name("cert2")
                .description("bad")
                .price(new BigDecimal("7"))
                .createDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .lastUpdateDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .duration(90)
                .build();

        certificateEntity3 = CertificateEntity.builder()
                .id(CERTIFICATE_ID_3)
                .name("cert3")
                .description("bad")
                .price(new BigDecimal("8"))
                .createDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .lastUpdateDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .duration(90)
                .build();

        certificateEntity4 = CertificateEntity.builder()
                .id(CERTIFICATE_ID_4)
                .name("cert4")
                .description("nice")
                .price(new BigDecimal("50"))
                .createDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .lastUpdateDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .duration(90)
                .build();
        tagEntity1 = TagEntity.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();
        tagEntity3 = TagEntity.builder()
                .id(TAG_ID_3)
                .name("tag3")
                .build();
    }

    @Autowired
    private CertificateRepository certificateRepository;

    @Test
    void create() {
        CertificateEntity actual = certificateRepository.create(certificateEntity4);
        Assertions.assertEquals(certificateEntity4, actual);
    }

    @Test
    void readCertificateTags() {
        List<TagEntity> expectedTagEntities = List.of(tagEntity1);
        List<TagEntity> actualTagEntities = certificateRepository.readCertificateTags(certificateEntity2.getId());
        Assertions.assertEquals(expectedTagEntities, actualTagEntities);
    }

    @Test
    void readById() {
        Optional<CertificateEntity> actual = certificateRepository.readById(CERTIFICATE_ID_1);
        Assertions.assertEquals(Optional.of(certificateEntity1), actual);
    }

    @Test
    void readByName() {
        CertificateEntity actual = certificateRepository.readByName(CERTIFICATE_NAME);
        Assertions.assertEquals(Optional.of(certificateEntity1), actual);
    }

    @Test
    void addTag() {
        List<TagEntity> expected = List.of(tagEntity1, tagEntity3);
        certificateRepository.addTag(TAG_ID_3, certificateEntity2.getId());
        List<TagEntity> actual = certificateRepository.readCertificateTags(certificateEntity2.getId());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readAll() {
        List<CertificateEntity> actual = certificateRepository.readAll();
        List<CertificateEntity> expected = Arrays.asList(certificateEntity1, certificateEntity2, certificateEntity3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        int actual = certificateRepository.delete(CERTIFICATE_ID_1);
        Assertions.assertEquals(1, actual);
    }

    @Test
    void update() {
        CertificateEntity certificateEntity = new CertificateEntity();
        certificateEntity.setId(CERTIFICATE_ID_3);
        certificateEntity.setName("cert5");
        certificateEntity.setDescription("wow!");
        certificateEntity.setPrice(new BigDecimal("100.00"));
        certificateEntity.setDuration(90);
        certificateEntity.setCreateDate(LocalDateTime.parse("2021-12-14T11:45:11"));
        certificateEntity.setLastUpdateDate(LocalDateTime.parse("2021-12-14T11:45:11"));
        Optional<CertificateEntity> expected = Optional.of(certificateEntity);
        certificateRepository.update(CERTIFICATE_ID_3, certificateEntity);
        Optional<CertificateEntity> actual = certificateRepository.readById(CERTIFICATE_ID_3);
        Assertions.assertEquals(expected, actual);
    }
}

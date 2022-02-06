package com.epam.esm.dao.impl;

import com.epam.esm.configuration.ApplicationConfigurationTest;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
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
public class CertificateRepositoryImplTest {
    private static final Long CERTIFICATE_ID_1 = 1L;
    private static final Long CERTIFICATE_ID_2 = 2L;
    private static final Long CERTIFICATE_ID_3 = 3L;
    private static final Long CERTIFICATE_ID_4 = 4L;
    private static final Long TAG_ID_1 = 1L;
    private static final Long TAG_ID_3 = 3L;

    private static final String CERTIFICATE_NAME = "cert1";

    private Certificate certificate1;
    private Certificate certificate2;
    private Certificate certificate3;
    private Certificate certificate4;
    private Tag tag1;
    private Tag tag3;

    @BeforeEach
    public void setUp() {
        certificate1 = Certificate.builder()
                .id(CERTIFICATE_ID_1)
                .name("cert1")
                .description("nice")
                .price(new BigDecimal("5"))
                .createDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .lastUpdateDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .duration(90)
                .build();

        certificate2 = Certificate.builder()
                .id(CERTIFICATE_ID_2)
                .name("cert2")
                .description("bad")
                .price(new BigDecimal("7"))
                .createDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .lastUpdateDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .duration(90)
                .build();

        certificate3 = Certificate.builder()
                .id(CERTIFICATE_ID_3)
                .name("cert3")
                .description("bad")
                .price(new BigDecimal("8"))
                .createDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .lastUpdateDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .duration(90)
                .build();

        certificate4 = Certificate.builder()
                .id(CERTIFICATE_ID_4)
                .name("cert4")
                .description("nice")
                .price(new BigDecimal("50"))
                .createDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .lastUpdateDate(LocalDateTime.parse("2022-02-06T00:39:00"))
                .duration(90)
                .build();
        tag1 = Tag.builder()
                .id(TAG_ID_1)
                .name("tag1")
                .build();
        tag3 = Tag.builder()
                .id(TAG_ID_3)
                .name("tag3")
                .build();
    }

    @Autowired
    private CertificateRepository certificateRepository;

    @Test
    void create() {
        Certificate actual = certificateRepository.create(certificate4);
        Assertions.assertEquals(certificate4, actual);
    }

    @Test
    void readCertificateTags() {
        List<Tag> expectedTags = List.of(tag1);
        List<Tag> actualTags = certificateRepository.readCertificateTags(certificate2.getId());
        Assertions.assertEquals(expectedTags, actualTags);
    }

    @Test
    void readById() {
        Optional<Certificate> actual = certificateRepository.readById(CERTIFICATE_ID_1);
        Assertions.assertEquals(Optional.of(certificate1), actual);
    }

    @Test
    void readByName() {
        Optional<Certificate> actual = certificateRepository.readByName(CERTIFICATE_NAME);
        Assertions.assertEquals(Optional.of(certificate1), actual);
    }

    @Test
    void addTag() {
        List<Tag> expected = List.of(tag1, tag3);
        certificateRepository.addTag(TAG_ID_3, certificate2.getId());
        List<Tag> actual = certificateRepository.readCertificateTags(certificate2.getId());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readAll() {
        List<Certificate> actual = certificateRepository.readAll();
        List<Certificate> expected = Arrays.asList(certificate1, certificate2, certificate3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        int actual = certificateRepository.delete(CERTIFICATE_ID_1);
        Assertions.assertEquals(1, actual);
    }

    @Test
    void update() {
        Certificate certificate = new Certificate();
        certificate.setId(CERTIFICATE_ID_3);
        certificate.setName("cert5");
        certificate.setDescription("wow!");
        certificate.setPrice(new BigDecimal("100.00"));
        certificate.setDuration(90);
        certificate.setCreateDate(LocalDateTime.parse("2021-12-14T11:45:11"));
        certificate.setLastUpdateDate(LocalDateTime.parse("2021-12-14T11:45:11"));
        Optional<Certificate> expected = Optional.of(certificate);
        certificateRepository.update(CERTIFICATE_ID_3, certificate);
        Optional<Certificate> actual = certificateRepository.readById(CERTIFICATE_ID_3);
        Assertions.assertEquals(expected, actual);
    }
}

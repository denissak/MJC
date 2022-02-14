package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.Search;
import com.epam.esm.mapper.CertificateRowMapper;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Contains methods implementation for working mostly with
 * {@code Certificate} entity.
 */
@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    private static final String GET_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?";
    private static final String GET_CERTIFICATE_BY_NAME = "SELECT * FROM gift_certificate WHERE name = ?";
    private static final String GET_ALL_CERTIFICATES = "SELECT * FROM gift_certificate";
    private static final String GET_ALL_TAGS_BY_CERTIFICATE_ID = "SELECT id, name FROM tag t JOIN gift_certificate_m2m_tag m2m " +
            "ON t.id=m2m.tag_id WHERE gift_certificate_id = ?";
    private static final String SAVE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration," +
            " create_date, last_update_date) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET name = ?, description = ?, price = ?, " +
            "duration = ?, create_date = ?, last_update_date = ? WHERE id = ?";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String ADD_TAG = "INSERT INTO gift_certificate_m2m_tag (tag_id, gift_certificate_id) " +
            "VALUES (?, ?)";

    private JdbcTemplate jdbcTemplate;
    private Search search;
    private CertificateRowMapper certificateRowMapper;
    private TagRowMapper tagRowMapper;

    @Autowired
    public CertificateRepositoryImpl(JdbcTemplate jdbcTemplate, Search search, CertificateRowMapper certificateRowMapper, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.certificateRowMapper = certificateRowMapper;
        this.tagRowMapper = tagRowMapper;
        this.search = search;
    }

    /**
     * Saves the passed certificate.
     *
     * @param certificate the certificate to be saved
     * @return saved certificate
     */
    @Override
    public Certificate create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SAVE_CERTIFICATE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, certificate.getName());
            preparedStatement.setString(2, certificate.getDescription());
            preparedStatement.setDouble(3, certificate.getPrice());
            preparedStatement.setInt(4, certificate.getDuration());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(certificate.getCreateDate()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
            return preparedStatement;
        }, keyHolder);
        Long id = (Long) keyHolder.getKeyList().get(0).get("id");
        certificate.setId(id);
        return certificate;
    }

    /**
     * Reads certificate tags with passed id.
     *
     * @param certificateId the id of certificate to be read
     * @return will return the tags belonging to the certificate
     */
    @Override
    public List<Tag> readCertificateTags(long certificateId) {
        return jdbcTemplate.query(GET_ALL_TAGS_BY_CERTIFICATE_ID, tagRowMapper, certificateId);
    }

    /**
     * Reads certificate with passed id.
     *
     * @param id the id of certificate to be read
     * @return certificate with passed id
     */
    @Override
    public Optional<Certificate> readById(Long id) {
        List<Certificate> certificateList = jdbcTemplate.query(GET_CERTIFICATE_BY_ID, certificateRowMapper, id);
        if (certificateList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(certificateList.get(0));
    }

    /**
     * Reads certificate with passed name.
     *
     * @param name the id of certificate to be read
     * @return certificate with passed name
     */
    @Override
    public Optional<Certificate> readByName(String name) {
        List<Certificate> certificateList = jdbcTemplate.query(GET_CERTIFICATE_BY_NAME, certificateRowMapper, name);
        if (certificateList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(certificateList.get(0));
    }

    /**
     * Attach tags to the certificate
     *
     * @param tagId         tag id which needs to set in m2m table
     * @param certificateId certificate id which need to set in m2m table
     */
    @Override
    public void addTag(long tagId, long certificateId) {
        jdbcTemplate.update(ADD_TAG, tagId, certificateId);
    }

    /**
     * Reads all certificates.
     *
     * @return all certificates
     */
    @Override
    public List<Certificate> readAll() {
        return jdbcTemplate.query(GET_ALL_CERTIFICATES, certificateRowMapper);
    }

    /**
     * Search by specified certificate values
     *
     * @param tagValue    tag name
     * @param name        whole or partial certificate name
     * @param description whole or partial certificate description
     * @param sortBy      Sort target field (name or date)
     * @param sortOrder   Sort type (asc or desc)
     *
     * @return all certificates from search terms
     */
    @Override
    public List<Certificate> readCertificateWithDifferentParams(String tagValue, String name, String description, String sortBy, String sortOrder) {
        return jdbcTemplate.query(search.buildSearchCertificate(tagValue, name, description, sortBy, sortOrder), certificateRowMapper);
    }

    /**
     * Deletes certificate with passed id.
     *
     * @param id the id of entity to be deleted
     * @return the number of deleted entities
     */
    @Override
    public Integer delete(Long id) {
        return jdbcTemplate.update(DELETE_CERTIFICATE, id);
    }

    /**
     * Updates certificate fields with passed id
     *
     * @param id          certificate id which needs to be updated
     * @param certificate certificate entity which contains fields with new
     *                    values to be set
     */
    @Override
    public void update(long id, Certificate certificate) {
        jdbcTemplate.update(UPDATE_CERTIFICATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate(),
                id);
    }
}

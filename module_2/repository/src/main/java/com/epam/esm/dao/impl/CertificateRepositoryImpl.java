package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.mapper.CertificateRowMapper;
import com.epam.esm.mapper.TagRowMapper;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    private static final String REMOVE_TAG = "DELETE FROM gift_certificate_m2m_tag WHERE " +
            "gift_certificate_id = ? AND tag_id = ?";
    private static final String REMOVE_ALL_TAGS = "DELETE FROM gift_certificate_m2m_tag WHERE gift_certificate_id = ?";
    private static final String ADD_TAG = "INSERT INTO gift_certificate_m2m_tag (tag_id, gift_certificate_id) " +
            "VALUES (?, ?)";

    private JdbcTemplate jdbcTemplate;
    private CertificateRowMapper certificateRowMapper;
    private TagRowMapper tagRowMapper;

    @Autowired
    public CertificateRepositoryImpl(JdbcTemplate jdbcTemplate, CertificateRowMapper certificateRowMapper, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.certificateRowMapper = certificateRowMapper;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public Certificate create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SAVE_CERTIFICATE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, certificate.getName());
            preparedStatement.setString(2, certificate.getDescription());
            preparedStatement.setBigDecimal(3, certificate.getPrice());
            preparedStatement.setInt(4, certificate.getDuration());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(certificate.getCreateDate()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
            return preparedStatement;
        }, keyHolder);
        certificate.setId(Objects.requireNonNull(keyHolder.getKey()).longValue()); //TODO
        return certificate;
    }

    @Override
    public void addTag(long tagId, long certificateId) {
        jdbcTemplate.update(ADD_TAG, certificateId, certificateRowMapper);
    }

    @Override
    public void removeTag(long tagId, long certificateId) {
        jdbcTemplate.update(REMOVE_TAG, certificateId, certificateRowMapper);
    }

    @Override
    public List<Tag> readCertificateTags(long certificateId) {
        return jdbcTemplate.query(GET_ALL_TAGS_BY_CERTIFICATE_ID, tagRowMapper, certificateId);
    }

    @Override
    public Optional<Certificate> readById(Long id) {
        List<Certificate> certificateList = jdbcTemplate.query(GET_CERTIFICATE_BY_ID, certificateRowMapper, id);
        if (certificateList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(certificateList.get(0));
    }

    @Override
    public Optional<Certificate> readByName(String name) {
        List<Certificate> certificateList = jdbcTemplate.query(GET_CERTIFICATE_BY_NAME, certificateRowMapper, name);
        if (certificateList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(certificateList.get(0));
    }

    @Override
    public List<Certificate> readAll() {
        return jdbcTemplate.query(GET_ALL_CERTIFICATES, certificateRowMapper);
    }

    @Override
    public List<Certificate> readCertificateWithDifferentParams(String tagValue, String query, String sort, boolean ascending) {
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withProcedureName("procedure").returningResultSet("certificates", certificateRowMapper);
        Map out = call.execute(new MapSqlParameterSource()
                .addValue("tagValue", tagValue)
                .addValue("query", query)
                .addValue("sortBy", sort)
                .addValue("ascending", ascending));
        List certificates = (List) out.get("certificates");
        return certificates;
    }

    @Override
    public Integer delete(Long id) {
        return jdbcTemplate.update(DELETE_CERTIFICATE, id);
    }

    @Override
    public Optional<Certificate> update(Certificate certificate) {
        jdbcTemplate.update(UPDATE_CERTIFICATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate());
        return Optional.of(certificate);
    }

}

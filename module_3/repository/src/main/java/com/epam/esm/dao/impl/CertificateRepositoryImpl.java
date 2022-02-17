package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.Search;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.CertificateEntity_;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.entity.TagEntity_;
import com.epam.esm.mapper.CertificateRowMapper;
import com.epam.esm.mapper.TagRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/**
 * Contains methods implementation for working mostly with
 * {@code Certificate} entity.
 */
@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    @PersistenceContext
    private EntityManager entityManager;

//    private static final String GET_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?";
//    private static final String GET_CERTIFICATE_BY_NAME = "SELECT * FROM gift_certificate WHERE name = ?";
//    private static final String GET_ALL_CERTIFICATES = "SELECT * FROM gift_certificate";
//    private static final String GET_ALL_TAGS_BY_CERTIFICATE_ID = "SELECT id, name FROM tag t JOIN gift_certificate_m2m_tag m2m " +
//            "ON t.id=m2m.tag_id WHERE gift_certificate_id = :certificate_id";
//    private static final String SAVE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration," +
//            " create_date, last_update_date) VALUES (?,?,?,?,?,?)";
//    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET name = ?, description = ?, price = ?, " +
//            "duration = ?, create_date = ?, last_update_date = ? WHERE id = ?";
//    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = ?";
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
     * @param certificateEntity the certificate to be saved
     * @return saved certificate
     */
    @Override
    public CertificateEntity create(CertificateEntity certificateEntity) {
        entityManager.persist(certificateEntity);
        return certificateEntity;
    }

    /**
     * Reads certificate tagEntities with passed id.
     *
     * @param certificateId the id of certificate to be read
     * @return will return the tagEntities belonging to the certificate
     */
    @Override
    public List<TagEntity> readCertificateTags(long certificateId) {//TODO
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = cb.createQuery(TagEntity.class);
        Root<TagEntity> tagEntityRoot = criteria.from(TagEntity.class);
        var certificates = tagEntityRoot.join(TagEntity_.certificateEntity);
        criteria.select(tagEntityRoot).where(cb.equal(certificates.get(CertificateEntity_.id), certificateId));



/*        var resultList = entityManager.createQuery("SELECT t.id, t.name FROM TagEntity t join fetch CertificateEntity c on c.id= :certId", TagEntity.class)
                .setParameter("certId",certificateId)
                .getResultList();*/
/*        var resultList= entityManager.createQuery(GET_ALL_TAGS_BY_CERTIFICATE_ID, TagEntity.class)
                .setParameter("certificate_id",certificateId)
                .getResultList();*/
//        return resultList;
        return entityManager.createQuery(criteria).getResultList();
    }

    /**
     * Reads certificate with passed id.
     *
     * @param id the id of certificate to be read
     * @return certificate with passed id
     */
    @Override
    public CertificateEntity readById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CertificateEntity> criteria = cb.createQuery(CertificateEntity.class);
        Root<CertificateEntity> certificateEntityRoot = criteria.from(CertificateEntity.class);
        criteria.select(certificateEntityRoot)
                .where(cb.equal(certificateEntityRoot.get("id"), id));
        List<CertificateEntity> certificateEntityList = entityManager.createQuery(criteria).getResultList();
        if (certificateEntityList.size() > 0) {
            return certificateEntityList.get(0);
        } else
            return null;
    }

    /**
     * Reads certificate with passed name.
     *
     * @param name the id of certificate to be read
     * @return certificate with passed name
     */
    @Override
    public CertificateEntity readByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CertificateEntity> criteria = cb.createQuery(CertificateEntity.class);
        Root<CertificateEntity> certificateEntityRoot = criteria.from(CertificateEntity.class);
        criteria.select(certificateEntityRoot)
                .where(cb.equal(certificateEntityRoot.get("name"), name));
        List<CertificateEntity> certificateEntityList = entityManager.createQuery(criteria).getResultList();
        if (certificateEntityList.size() > 0) {
            return certificateEntityList.get(0);
        } else
            return null;
    }

    /**
     * Attach tagEntities to the certificate
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
    public List<CertificateEntity> readAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CertificateEntity> criteria = cb.createQuery(CertificateEntity.class);
        Root<CertificateEntity> certificateEntityRoot = criteria.from(CertificateEntity.class);
        criteria.select(certificateEntityRoot);
        return entityManager.createQuery(criteria).getResultList();
    }

    /**
     * Search by specified certificate values
     *
     * @param tagValue    tag name
     * @param name        whole or partial certificate name
     * @param description whole or partial certificate description
     * @param sortBy      Sort target field (name or date)
     * @param sortOrder   Sort type (asc or desc)
     * @return all certificates from search terms
     */
    @Override
    public List<CertificateEntity> readCertificateWithDifferentParams(String tagValue, String name, String description, String sortBy, String sortOrder) {
        return jdbcTemplate.query(search.buildSearchCertificate(tagValue, name, description, sortBy, sortOrder), certificateRowMapper);
    }

    /**
     * Deletes certificate with passed id.
     *
     * @param id the id of entity to be deleted
     */
    @Override
    public void delete(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CertificateEntity> criteria = cb.createQuery(CertificateEntity.class);
        Root<CertificateEntity> certificateEntityRoot = criteria.from(CertificateEntity.class);
        criteria.select(certificateEntityRoot)
                .where(cb.equal(certificateEntityRoot.get("id"), id));
        CertificateEntity certificateEntity = entityManager.createQuery(criteria).getResultList().get(0);
        entityManager.remove(certificateEntity);
    }

    /**
     * Updates certificate fields with passed id
     *
     * @param id                certificate id which needs to be updated
     * @param certificateEntity certificate entity which contains fields with new
     *                          values to be set
     */
    @Override
    public void update(long id, CertificateEntity certificateEntity) { //TODO UPDATE WITH NEW TAG CRASH
        // CertificateEntity certificate = entityManager.find(CertificateEntity.class, id);
        entityManager.merge(certificateEntity);
/*        jdbcTemplate.update(UPDATE_CERTIFICATE,
                certificateEntity.getName(),
                certificateEntity.getDescription(),
                certificateEntity.getPrice(),
                certificateEntity.getDuration(),
                certificateEntity.getCreateDate(),
                certificateEntity.getLastUpdateDate(),
                id);*/
    }
}

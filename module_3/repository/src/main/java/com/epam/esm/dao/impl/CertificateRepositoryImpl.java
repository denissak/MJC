package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.PaginationHandler;
import com.epam.esm.dao.Search;
import com.epam.esm.entity.CertificateEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Contains methods implementation for working mostly with
 * {@code Certificate} entity.
 */
@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String ADD_TAG = "INSERT INTO gift_certificate_m2m_tag (tag_id, gift_certificate_id) " +
            "VALUES (?, ?)";

    private final Search search;
    private final PaginationHandler paginationHandler;

    @Autowired
    public CertificateRepositoryImpl(Search search, PaginationHandler paginationHandler) {
        this.search = search;
        this.paginationHandler = paginationHandler;
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
     * Reads certificate with passed id.
     *
     * @param id the id of certificate to be read
     * @return certificate with passed id
     */
    @Override
    public CertificateEntity readById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CertificateEntity> criteria = criteriaBuilder.createQuery(CertificateEntity.class);
        Root<CertificateEntity> certificateEntityRoot = criteria.from(CertificateEntity.class);
        criteria.select(certificateEntityRoot)
                .where(criteriaBuilder.equal(certificateEntityRoot.get("id"), id));
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CertificateEntity> criteria = criteriaBuilder.createQuery(CertificateEntity.class);
        Root<CertificateEntity> certificateEntityRoot = criteria.from(CertificateEntity.class);
        criteria.select(certificateEntityRoot)
                .where(criteriaBuilder.equal(certificateEntityRoot.get("name"), name));
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
        entityManager.createNativeQuery(ADD_TAG).setParameter(1, tagId).setParameter(2, certificateId);
    }

    /**
     * Reads all certificates.
     *
     * @param page numbers of page
     * @param size number of elements per page
     *
     * @return all certificates
     */
    @Override
    public List<CertificateEntity> readAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CertificateEntity> criteria = criteriaBuilder.createQuery(CertificateEntity.class);
        Root<CertificateEntity> certificateEntityRoot = criteria.from(CertificateEntity.class);
        CriteriaQuery<CertificateEntity> select = criteria.select(certificateEntityRoot);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(CertificateEntity.class)));
        TypedQuery<CertificateEntity> typedQuery = entityManager.createQuery(select);
        paginationHandler.setPageToQuery(typedQuery, page, size);
        return typedQuery.getResultList();
    }

    /**
     * Search by specified certificate values
     *
     * @param tagValue    tag name
     * @param name        whole or partial certificate name
     * @param description whole or partial certificate description
     * @param sortBy      Sort target field (name or date)
     * @param sortOrder   Sort type (asc or desc)
     * @param page numbers of page
     * @param size number of elements per page
     * @return all certificates from search terms
     */
    @Override
    public List<CertificateEntity> readCertificateWithDifferentParams(String[] tagValue, String name, String description, String sortBy, String sortOrder, int page, int size) {
        return entityManager.createNativeQuery(search.buildSearchCertificate(tagValue, name, description, sortBy, sortOrder), CertificateEntity.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    /**
     * Deletes certificate with passed id.
     *
     * @param id the id of entity to be deleted
     */
    @Override
    public void delete(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CertificateEntity> criteria = criteriaBuilder.createQuery(CertificateEntity.class);
        Root<CertificateEntity> certificateEntityRoot = criteria.from(CertificateEntity.class);
        criteria.select(certificateEntityRoot)
                .where(criteriaBuilder.equal(certificateEntityRoot.get("id"), id));
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
    public void update(long id, CertificateEntity certificateEntity) {
        entityManager.merge(certificateEntity);
    }
}

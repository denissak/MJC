package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateFilterRepository;
import com.epam.esm.dao.Search;
import com.epam.esm.entity.CertificateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CertificateFilterRepositoryImpl implements CertificateFilterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final Search search;

    @Autowired
    public CertificateFilterRepositoryImpl(EntityManager entityManager, Search search) {
        this.entityManager = entityManager;
        this.search = search;
    }

    /**
     * Search by specified certificate values
     *
     * @param tagValue    tag name
     * @param name        whole or partial certificate name
     * @param description whole or partial certificate description
     * @param sortBy      Sort target field (name or date)
     * @param sortOrder   Sort type (asc or desc)
     * @param page        numbers of page
     * @param size        number of elements per page
     * @return all certificates from search terms
     */

    @Override
    public List<CertificateEntity> readCertificateWithDifferentParams(String[] tagValue, String name, String description, String sortBy, String sortOrder, int page, int size) {
                return entityManager.createNativeQuery(search.buildSearchCertificate(tagValue, name, description, sortBy, sortOrder), CertificateEntity.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }
}

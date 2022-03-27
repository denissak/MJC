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

    @Override
    public List<CertificateEntity> readCertificateWithDifferentParams(String[] tagValue, String name, String description, String sortBy, String sortOrder, int page, int size) {
                return entityManager.createNativeQuery(search.buildSearchCertificate(tagValue, name, description, sortBy, sortOrder), CertificateEntity.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }
}

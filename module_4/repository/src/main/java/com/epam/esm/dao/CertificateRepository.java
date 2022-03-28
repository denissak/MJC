package com.epam.esm.dao;

import com.epam.esm.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contains methods for working mostly with {@code Certificate} entity.
 */
public interface CertificateRepository extends JpaRepository<CertificateEntity, Long>, CertificateFilterRepository {

    /**
     * Reads CertificateEntity with passed name.
     *
     * @param certificateName the name of entity to be read
     * @return entity with passed name
     */
    CertificateEntity readByName(String certificateName);
}

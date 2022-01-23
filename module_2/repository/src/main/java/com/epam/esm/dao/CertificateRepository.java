package com.epam.esm.dao;

import com.epam.esm.model.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends AbstractRepository<Certificate>{

    Optional<Certificate> update (Certificate certificate);

    void addTag (Long tagId, Long certificateId);

    void removeTag (Long tagId, Long certificateId);

    List<Certificate> readByTagId (Long tagId);
}

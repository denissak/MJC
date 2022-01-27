package com.epam.esm.dao;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends AbstractRepository<Certificate>{

    Optional<Certificate> update (Certificate certificate);

    void addTag (long tagId, long certificateId);

    void removeTag (long tagId, long certificateId);

    List<Certificate> readCertificateWithDifferentParams (String tagValue, String query, String sort, boolean ascending);

    List<Tag> readCertificateTags (long certificateId);
}

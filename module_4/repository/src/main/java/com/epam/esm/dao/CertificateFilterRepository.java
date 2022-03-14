package com.epam.esm.dao;

import com.epam.esm.entity.CertificateEntity;

import java.util.List;

public interface CertificateFilterRepository {

        List<CertificateEntity> readCertificateWithDifferentParams(String[] tagValue, String name, String description,
                                                                   String sortBy, String sortOrder, int page, int size);
}

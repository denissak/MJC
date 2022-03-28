package com.epam.esm.dao;

import com.epam.esm.entity.CertificateEntity;

import java.util.List;

/**
 * Contains method for search {@code Certificate} entity.
 */
public interface CertificateFilterRepository {

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

    List<CertificateEntity> readCertificateWithDifferentParams(String[] tagValue, String name, String description,
                                                               String sortBy, String sortOrder, int page, int size);
}

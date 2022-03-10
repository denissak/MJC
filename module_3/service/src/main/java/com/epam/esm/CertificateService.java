package com.epam.esm;

import com.epam.esm.dto.CertificateDto;

import java.util.List;

/**
 * Contains methods for working mostly with {@code CertificateDto} entity.
 */
public interface CertificateService {

    /**
     * Creates and saves the passed certificate.
     *
     * @param certificateDto the certificate to be saved
     * @return saved certificate
     */
    CertificateDto create(CertificateDto certificateDto);

    /**
     * Reads certificate with passed id.
     *
     * @param certificateId the id of certificate to be read
     * @return certificate with passed id
     */
    CertificateDto readById(Long certificateId);

    /**
     * Reads all certificates.
     *
     * @param page numbers of page
     * @param size number of elements per page
     *
     * @return all certificates
     */
    List<CertificateDto> readAll(int page, int size);

    /**
     * Updates certificate fields with passed id
     *
     * @param id             certificate id which needs to be updated
     * @param certificateDto certificate entity which contains fields with new
     *                       values to be set
     */
    CertificateDto update(long id, CertificateDto certificateDto);

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
     *
     * @return all certificates from search terms
     */
    List<CertificateDto> readCertificateWithDifferentParams(String[] tagValue, String name, String description, String sortBy, String sortOrder, int page, int size);

    /**
     * Deletes certificate with passed id.
     *
     * @param certificateId the id of certificate to be deleted
     */
    void delete(Long certificateId);
}

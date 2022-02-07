package com.epam.esm.dao;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import java.util.List;

/**
 *
 * Contains methods for working mostly with {@code Certificate} entity.
 *
 */
public interface CertificateRepository extends AbstractRepository<Certificate>{

    /**
     * Updates certificate fields with passed id
     *
     * @param id certificate id which needs to be updated
     * @param certificate certificate entity which contains fields with new
     *                         values to be set
     */
    void update (long id, Certificate certificate);

    /**
     * Attach tags to the certificate
     *
     * @param tagId tag id which needs to set in m2m table
     * @param certificateId certificate id which need to set in m2m table
     */
    void addTag (long tagId, long certificateId);

    /**
     * Search by specified certificate values
     *
     * @param tagValue    tag name
     * @param name        whole or partial certificate name
     * @param description whole or partial certificate description
     * @param sortBy      Sort target field (name or date)
     * @param sortOrder   Sort type (asc or desc)
     *
     * @return all certificates from search terms
     */
    List<Certificate> readCertificateWithDifferentParams (String tagValue, String name, String description, String sortBy, String sortOrder);

    /**
     * Search all tags
     *
     * @param certificateId certificate id with all tags
     *
     * @return All tags by certificate
     */
    List<Tag> readCertificateTags (long certificateId);
}

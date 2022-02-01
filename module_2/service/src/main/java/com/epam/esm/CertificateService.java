package com.epam.esm;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.model.Certificate;
import org.springframework.stereotype.Component;

import java.util.List;

public interface CertificateService {

    CertificateDto create(CertificateDto certificateDto);

    CertificateDto readById(Long certificateId);

    List<CertificateDto> readAll();

    CertificateDto update(Long certificateId, CertificateDto certificateDto);

    List<CertificateDto> readCertificateWithDifferentParams(String tagValue, String name, String description, String sortBy, String sortOrder);

    void delete(Long certificateId);

}

package com.epam.esm;

import com.epam.esm.dto.CertificateDto;

import java.util.List;

public interface CertificateService {

    CertificateDto create (CertificateDto certificateDto);

    CertificateDto readById (Long certificateId);

    List<CertificateDto> readAll ();

    CertificateDto update (Long certificateId, CertificateDto certificateDto);
}
